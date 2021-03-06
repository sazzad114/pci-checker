package com.pci.checker.service;

import com.pci.checker.model.AnalysisResult;
import com.pci.checker.model.CertAnalaysisResult;
import com.pci.checker.util.Utils;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AnalysisService {

    private static final String[] VUL_OPENSSH = new String[]{"OpenSSH_5.", "OpenSSH_6.", "OpenSSH_7.1", "OpenSSH_7.2", "OpenSSH_7.3", "OpenSSH_7.4", "OpenSSH_7.5", "OpenSSH_7.6"};

    private static final String SERVER_KEY = "Server";

    private static final String HEADER_X_FRAME = "X-Frame-Options";
    private static final String HEADER_XSS = "X-XSS-Protection";
    private static final String HEADER_STRICT_TS = "Strict-Transport-Security";
    private static final String HEADER_CONTENT_TO = "X-Content-Type-Options";

    private static final CertAnalysisService certAnalysisService = new CertAnalysisService();

    public AnalysisResult getAnalysisResult(String domainName) {

        AnalysisResult analysisResult = new AnalysisResult();

        String redirectURL = Utils.getRedirectedUrl(domainName);

        analysisResult.setRedirectUrl(redirectURL);

        if (redirectURL != null) {
            analysisResult.setRedirectedToHttp(redirectURL.contains("http://"));
        } else {
            redirectURL = "http://" + domainName;
        }

        try {
            domainName = Utils.getdomainNameFromUrl(redirectURL);
        } catch (MalformedURLException e) {
            System.err.println("error handling domain: " + domainName);
            e.printStackTrace(System.err);
        }

        try {
            CertAnalaysisResult certAnalaysisResult = certAnalysisService.analyzeCert(domainName);
            analysisResult.setCertAnalaysisResult(certAnalaysisResult);
        } catch (Exception e) {
            System.err.println("error handling domain: " + domainName);
            e.printStackTrace(System.err);
        }

        try {

            String openSSHVersion = Utils.getOpenSshVersion(domainName);

            if (openSSHVersion.contains("OpenSSH")) {
                analysisResult.setOpensshAvailable(true);
            }

            for (String vul : VUL_OPENSSH) {
                if (openSSHVersion.contains(vul)) {
                    analysisResult.setOpensshVulnerable(true);
                    break;
                }
            }

            analysisResult.setOpensshVersion(openSSHVersion);
        } catch (Exception e) {
            System.err.println("error handling domain: " + domainName);
            e.printStackTrace(System.err);
        }


        try {

            Map<String, List<String>> headers = Utils.getHeaders(domainName);

            List<String> serverInfo = headers.get(SERVER_KEY);

            if (serverInfo != null) {
                analysisResult.setServerInfo(headers.get(SERVER_KEY).toString());
                if (serverInfo.toString().matches("(.)+/[0-9](.)+")) { // [Apache/1.0, PHP/2.3]
                    analysisResult.setServerInfoAvailable(true);
                }
            }


            List<String> xframe = headers.get(HEADER_X_FRAME);

            if (xframe != null && !xframe.isEmpty()) {
                analysisResult.setXframeOptionAvailable(true);
                analysisResult.setXframeOption(xframe.toString());
            }

            List<String> hxss = headers.get(HEADER_XSS);

            if (hxss != null && !hxss.isEmpty()) {
                analysisResult.setXssHeaderAvailable(true);
                analysisResult.setXssHeader(hxss.toString());
            }

            List<String> hsts = headers.get(HEADER_STRICT_TS);

            if (hsts != null && !hsts.isEmpty()) {
                analysisResult.setStrictTransportAvailable(true);
                analysisResult.setStrictTransport(hsts.toString());
            }

            List<String> hcto = headers.get(HEADER_CONTENT_TO);

            if (hcto != null && !hcto.isEmpty()) {
                analysisResult.setContentTypeOptionsAvailable(true);
                analysisResult.setContentTypeOptions(hcto.toString());
            }

        } catch (Exception e) {
            System.err.println("error handling domain: " + domainName);
            e.printStackTrace(System.err);
        }

        try {

            analysisResult.setTlsv1Supported(Utils.isTls1Supported(domainName, "-tls1"));
            analysisResult.setSslv3Supported(Utils.isTls1Supported(domainName, "-ssl3"));
            analysisResult.setSslv2Supported(Utils.isTls1Supported(domainName, "-ssl2"));
        } catch (Exception e) {
            System.err.println("error handling domain: " + domainName);
            e.printStackTrace(System.err);
        }

        try {
            analysisResult.setWeakcipherSupported(Utils.isWeakCipherSupported(domainName));
        } catch (Exception e) {
            System.err.println("error handling domain: " + domainName);
            e.printStackTrace(System.err);
        }

        try {

            String mysqlConn = Utils.getMysqlVersion(domainName).trim();

            if (!mysqlConn.isEmpty()) {
                analysisResult.setMysqlAvailable(true);
                analysisResult.setDefaultMysqlPassword(Utils.isMysqlAccessible(domainName));
            }
        } catch (Exception e) {
            System.err.println("error handling domain: " + domainName);
            e.printStackTrace(System.err);
        }

        try {

            List<String> urlList = new ArrayList<>();

            analysisResult.setIntegrityCheck(Utils.missesIntegrityChecked(redirectURL, urlList));
            analysisResult.setBadScriptSrc(urlList);

            analysisResult.setBrowsableDirEnabled(Utils.isBrowseDirEnabled(redirectURL, urlList));


        } catch (Exception e) {
            System.err.println("error handling domain: " + domainName);
            e.printStackTrace(System.err);
        }

        try {

            int responseCode = Utils.isHttpTrackEnabled(domainName);

            analysisResult.setHttpTrackResCode(responseCode);

            boolean isEnabled = responseCode == HttpURLConnection.HTTP_OK;

            analysisResult.setHttpTrackEnabled(isEnabled);
        } catch (Exception e) {
            System.err.println("error handling domain: " + domainName);
            e.printStackTrace(System.err);
        }

        return analysisResult;

    }
}
