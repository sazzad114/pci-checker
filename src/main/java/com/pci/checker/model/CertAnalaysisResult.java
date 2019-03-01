package com.pci.checker.model;

public class CertAnalaysisResult {

    private boolean selfsigned;
    private boolean expired;
    private boolean wrongHostName;
    private boolean insecureModulus;
    private boolean weakHash;
    private String subjectdn;
    private String issuerdn;
    private String signatureAlg;
    private String pkname;
    private int pkSize;

    public boolean isSelfsigned() {
        return selfsigned;
    }

    public void setSelfsigned(boolean selfsigned) {
        this.selfsigned = selfsigned;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isWrongHostName() {
        return wrongHostName;
    }

    public void setWrongHostName(boolean wrongHostName) {
        this.wrongHostName = wrongHostName;
    }

    public boolean isInsecureModulus() {
        return insecureModulus;
    }

    public void setInsecureModulus(boolean insecureModulus) {
        this.insecureModulus = insecureModulus;
    }

    public boolean isWeakHash() {
        return weakHash;
    }

    public void setWeakHash(boolean weakHash) {
        this.weakHash = weakHash;
    }

    public String getSubjectdn() {
        return subjectdn;
    }

    public void setSubjectdn(String subjectdn) {
        this.subjectdn = subjectdn;
    }

    public String getIssuerdn() {
        return issuerdn;
    }

    public void setIssuerdn(String issuerdn) {
        this.issuerdn = issuerdn;
    }

    public String getSignatureAlg() {
        return signatureAlg;
    }

    public void setSignatureAlg(String signatureAlg) {
        this.signatureAlg = signatureAlg;
    }

    public String getPkname() {
        return pkname;
    }

    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    public int getPkSize() {
        return pkSize;
    }

    public void setPkSize(int pkSize) {
        this.pkSize = pkSize;
    }

    @Override
    public String toString() {
        return "Is selfsigned cert =" + selfsigned +
                "\n Is expired =" + expired +
                "\n Is Wrong HostName =" + wrongHostName +
                "\n Is Insecure Modulus =" + insecureModulus +
                "\n Is Weak Hash =" + weakHash +
                "\n Subject DN ='" + subjectdn + '\'' +
                "\n Issuer DN = '" + issuerdn + '\'' +
                "\n signature Alg ='" + signatureAlg + '\'' +
                "\n PK Type = '" + pkname + '\'' +
                "\n PK Size = " + pkSize;
    }
}
