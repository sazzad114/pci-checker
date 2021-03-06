package com.pci.checker.service;

import com.pci.checker.model.CertAnalaysisResult;
import com.pci.checker.util.Utils;
import org.bouncycastle.jce.provider.JCEECPublicKey;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.*;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CertAnalysisService {

    private static final String[] WEAK_HASH = new String[]{"MD5, SHA, SHA1, SHA-1"};

    private static final Map<String, Integer> PK_SIZE = new HashMap<>();

    static {
        PK_SIZE.put("RSA", 2048);
        PK_SIZE.put("DSA", 2048);
        PK_SIZE.put("EC", 224);
        PK_SIZE.put("ECDSA", 224);
    }

    public CertAnalaysisResult analyzeCert(String domainName) throws Exception {

        CertAnalaysisResult result = new CertAnalaysisResult();

        String encodedCert = crawlCertificateWithServerName(domainName);

        if (encodedCert.isEmpty()) {
            return result;
        }

        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        InputStream stream = new ByteArrayInputStream(encodedCert.getBytes(StandardCharsets.UTF_8));
        X509Certificate cert = (X509Certificate) fact.generateCertificate(stream);

        result.setExpired(isExpired(cert));    // 1
        result.setSelfsigned(isSelfSigned(cert)); // 2

        // Get subject
        Principal principal = cert.getSubjectDN();
        String subjectDn = principal.toString();
        result.setSubjectdn(subjectDn);


        principal = cert.getIssuerDN();
        String issuerDn = principal.getName();
        result.setIssuerdn(issuerDn);

        subjectDn = Utils.unStaredDn(subjectDn);

        boolean matched = domainName.toLowerCase().contains(subjectDn.toLowerCase());

        try {
            if (!matched) {
                for (String alt : getSubjectAlternativeNames(cert)) {
                    if (domainName.toLowerCase().contains(Utils.unStaredDn(alt).toLowerCase())) {
                        matched = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("error handling domain: " + domainName);
            e.printStackTrace(System.err);
        }


        result.setWrongHostName(!matched); // 3

        for (String hash : WEAK_HASH) {    // 4
            if (cert.getSigAlgName().contains(hash)) {
                result.setWeakHash(true);
            }
        }

        result.setSignatureAlg(cert.getSigAlgName());

        PublicKey pk = cert.getPublicKey();

        result.setPkname(pk.getAlgorithm());
        int pkSize = getKeyLength(pk);
        result.setPkSize(pkSize);

        for (String key : PK_SIZE.keySet()) {  // 5
            if (pk.getAlgorithm().contains(key) && pkSize < PK_SIZE.get(key)) {
                result.setInsecureModulus(true);
            }
        }

        return result;
    }

    /* @param certificate a certificate
     * @return a list of subject alternative names; list is never null
     * @throws CertificateParsingException if parsing the certificate failed
     */
    public static List<String> getSubjectAlternativeNames(final X509Certificate certificate) throws CertificateParsingException {
        final Collection<List<?>> altNames = certificate.getSubjectAlternativeNames();
        if (altNames == null) {
            return new ArrayList<>();
        }
        final List<String> result = new ArrayList<>();
        for (final List<?> generalName : altNames) {
            /**
             * generalName has the name type as the first element a String or byte array for the second element. We return any general names that are String types.
             *
             * We don't inspect the numeric name type because some certificates incorrectly put IPs and DNS names under the wrong name types.
             */
            final Object value = generalName.get(1);
            if (value instanceof String) {
                result.add(((String) value).toLowerCase());
            }
        }
        return result;
    }

    private static String crawlCertificateWithServerName(String domainName) throws Exception {

        ProcessBuilder pb = new
                ProcessBuilder("/bin/sh", "-c",
                String.format("timeout 2 openssl s_client -showcerts -connect %s:443 -servername %s </dev/null 2>/dev/null | openssl x509 -outform PEM", domainName, domainName));

        final Process p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder cert = new StringBuilder();

        String line;

        while ((line = br.readLine()) != null) {
            cert.append(line)
                    .append('\n');
        }

        return cert.toString();
    }

    private static boolean isExpired(X509Certificate cert) {
        try {
            cert.checkValidity();
        } catch (CertificateNotYetValidException | CertificateExpiredException e) {
            return true;
        }

        return false;
    }

    private static boolean isSelfSigned(X509Certificate cert)
            throws CertificateException, NoSuchAlgorithmException,
            NoSuchProviderException {
        try {
            // Try to verify certificate signature with its own public key
            PublicKey key = cert.getPublicKey();
            cert.verify(key);
            return true;
        } catch (SignatureException sigEx) {
            // Invalid signature --> not self-signed
            return false;
        } catch (InvalidKeyException keyEx) {
            // Invalid key --> not self-signed
            return false;
        }
    }

    /**
     * Gets the key length of supported keys
     *
     * @param pk PublicKey used to derive the keysize
     * @return -1 if key is unsupported, otherwise a number >= 0. 0 usually means the length can not be calculated,
     * for example if the key is an EC key and the "implicitlyCA" encoding is used.
     */
    public static int getKeyLength(final PublicKey pk) {
        int len = -1;
        if (pk instanceof RSAPublicKey) {
            final RSAPublicKey rsapub = (RSAPublicKey) pk;
            len = rsapub.getModulus().bitLength();
        } else if (pk instanceof JCEECPublicKey) {
            final JCEECPublicKey ecpriv = (JCEECPublicKey) pk;
            final org.bouncycastle.jce.spec.ECParameterSpec spec = ecpriv.getParameters();
            if (spec != null) {
                len = spec.getN().bitLength();
            } else {
                // We support the key, but we don't know the key length
                len = 0;
            }
        } else if (pk instanceof ECPublicKey) {
            final ECPublicKey ecpriv = (ECPublicKey) pk;
            final java.security.spec.ECParameterSpec spec = ecpriv.getParams();
            if (spec != null) {
                len = spec.getOrder().bitLength(); // does this really return something we expect?
            } else {
                // We support the key, but we don't know the key length
                len = 0;
            }
        } else if (pk instanceof DSAPublicKey) {
            final DSAPublicKey dsapub = (DSAPublicKey) pk;
            if (dsapub.getParams() != null) {
                len = dsapub.getParams().getP().bitLength();
            } else {
                len = dsapub.getY().bitLength();
            }
        }

        return len;
    }
}
