package com.pci.checker.model;

import java.util.ArrayList;
import java.util.List;

public class AnalysisResult {

    private CertAnalaysisResult certAnalaysisResult;

    private boolean redirectedToHttp;
    private String redirectUrl;

    private boolean opensshAvailable;
    private boolean opensshVulnerable;
    private String opensshVersion;

    private boolean serverInfoAvailable;
    private String serverInfo;

    private boolean xssHeaderAvailable;
    private String xssHeader;

    private boolean xframeOptionAvailable;
    private String xframeOption;

    private boolean contentTypeOptionsAvailable;
    private String contentTypeOptions;

    private boolean strictTransportAvailable;
    private String strictTransport;

    private boolean tlsv1Supported;
    private boolean sslv3Supported;
    private boolean sslv2Supported;

    private boolean weakcipherSupported;

    private boolean mysqlAvailable;
    private boolean defaultMysqlPassword;
    private boolean integrityCheck;
    private List<String> badScriptSrc;


    private boolean httpTrackEnabled;
    private int httpTrackResCode;

    private boolean browsableDirEnabled;

    public boolean isWeakcipherSupported() {
        return weakcipherSupported;
    }

    public void setWeakcipherSupported(boolean weakcipherSupported) {
        this.weakcipherSupported = weakcipherSupported;
    }

    public boolean isTlsv1Supported() {
        return tlsv1Supported;
    }

    public void setTlsv1Supported(boolean tlsv1Supported) {
        this.tlsv1Supported = tlsv1Supported;
    }

    public boolean isSslv3Supported() {
        return sslv3Supported;
    }

    public void setSslv3Supported(boolean sslv3Supported) {
        this.sslv3Supported = sslv3Supported;
    }

    public boolean isSslv2Supported() {
        return sslv2Supported;
    }

    public void setSslv2Supported(boolean sslv2Supported) {
        this.sslv2Supported = sslv2Supported;
    }

    public boolean isXssHeaderAvailable() {
        return xssHeaderAvailable;
    }

    public void setXssHeaderAvailable(boolean xssHeaderAvailable) {
        this.xssHeaderAvailable = xssHeaderAvailable;
    }

    public String getXssHeader() {
        return xssHeader;
    }

    public void setXssHeader(String xssHeader) {
        this.xssHeader = xssHeader;
    }

    public boolean isXframeOptionAvailable() {
        return xframeOptionAvailable;
    }

    public void setXframeOptionAvailable(boolean xframeOptionAvailable) {
        this.xframeOptionAvailable = xframeOptionAvailable;
    }

    public String getXframeOption() {
        return xframeOption;
    }

    public void setXframeOption(String xframeOption) {
        this.xframeOption = xframeOption;
    }

    public boolean isContentTypeOptionsAvailable() {
        return contentTypeOptionsAvailable;
    }

    public void setContentTypeOptionsAvailable(boolean contentTypeOptionsAvailable) {
        this.contentTypeOptionsAvailable = contentTypeOptionsAvailable;
    }

    public String getContentTypeOptions() {
        return contentTypeOptions;
    }

    public void setContentTypeOptions(String contentTypeOptions) {
        this.contentTypeOptions = contentTypeOptions;
    }

    public boolean isStrictTransportAvailable() {
        return strictTransportAvailable;
    }

    public void setStrictTransportAvailable(boolean strictTransportAvailable) {
        this.strictTransportAvailable = strictTransportAvailable;
    }

    public String getStrictTransport() {
        return strictTransport;
    }

    public void setStrictTransport(String strictTransport) {
        this.strictTransport = strictTransport;
    }

    public boolean isServerInfoAvailable() {
        return serverInfoAvailable;
    }

    public void setServerInfoAvailable(boolean serverInfoAvailable) {
        this.serverInfoAvailable = serverInfoAvailable;
    }

    public String getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    public boolean isOpensshAvailable() {
        return opensshAvailable;
    }

    public void setOpensshAvailable(boolean opensshAvailable) {
        this.opensshAvailable = opensshAvailable;
    }

    public boolean isOpensshVulnerable() {
        return opensshVulnerable;
    }

    public void setOpensshVulnerable(boolean opensshVulnerable) {
        this.opensshVulnerable = opensshVulnerable;
    }

    public String getOpensshVersion() {
        return opensshVersion;
    }

    public void setOpensshVersion(String opensshVersion) {
        this.opensshVersion = opensshVersion;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean isRedirectedToHttp() {
        return redirectedToHttp;
    }

    public void setRedirectedToHttp(boolean redirectedToHttp) {
        this.redirectedToHttp = redirectedToHttp;
    }

    public CertAnalaysisResult getCertAnalaysisResult() {
        return certAnalaysisResult;
    }

    public void setCertAnalaysisResult(CertAnalaysisResult certAnalaysisResult) {
        this.certAnalaysisResult = certAnalaysisResult;
    }

    public boolean isMysqlAvailable() {
        return mysqlAvailable;
    }

    public void setMysqlAvailable(boolean mysqlAvailable) {
        this.mysqlAvailable = mysqlAvailable;
    }

    public boolean isDefaultMysqlPassword() {
        return defaultMysqlPassword;
    }

    public void setDefaultMysqlPassword(boolean defaultMysqlPassword) {
        this.defaultMysqlPassword = defaultMysqlPassword;
    }

    public boolean isIntegrityCheck() {
        return integrityCheck;
    }

    public void setIntegrityCheck(boolean integrityCheck) {
        this.integrityCheck = integrityCheck;
    }

    public List<String> getBadScriptSrc() {
        return badScriptSrc;
    }

    public void setBadScriptSrc(List<String> badScriptSrc) {
        this.badScriptSrc = badScriptSrc;
    }

    public boolean isHttpTrackEnabled() {
        return httpTrackEnabled;
    }

    public void setHttpTrackEnabled(boolean httpTrackEnabled) {
        this.httpTrackEnabled = httpTrackEnabled;
    }

    public int getHttpTrackResCode() {
        return httpTrackResCode;
    }

    public void setHttpTrackResCode(int httpTrackResCode) {
        this.httpTrackResCode = httpTrackResCode;
    }

    public boolean isBrowsableDirEnabled() {
        return browsableDirEnabled;
    }

    public void setBrowsableDirEnabled(boolean browsableDirEnabled) {
        this.browsableDirEnabled = browsableDirEnabled;
    }

    @Override
    public String toString() {

        if (certAnalaysisResult == null) {
            certAnalaysisResult = new CertAnalaysisResult();
        }

        if (badScriptSrc == null) {
            badScriptSrc = new ArrayList<>();
        }

        return certAnalaysisResult.toString() +
                "\n 6. Is Redirected to HTTP = " + redirectedToHttp +
                "\n ..... Redirect URL = " + redirectUrl +
                "\n 7. Is OpenSSH available = " + opensshAvailable +
                "\n 8. Is OpenSSH vulnerable = " + opensshVulnerable +
                "\n ..... OpenSSH version = " + opensshVersion +
                "\n 9. Is Server Info available = " + serverInfoAvailable +
                "\n ..... Server Info = " + serverInfo +
                "\n 10a. Security Header: X-Frame-Options = " + xframeOptionAvailable +
                "\n ..... X-Frame-Options = " + xframeOption +
                "\n 10b. Security Header: X-XSS-Protection = " + xssHeaderAvailable +
                "\n ..... X-XSS-Protection = " + xssHeader +
                "\n 10c. Security Header: Strict-Transport-Security = " + strictTransportAvailable +
                "\n ..... Strict-Transport-Security = " + strictTransport +
                "\n 10d. Security Header: X-Content-Type-Options = " + contentTypeOptionsAvailable +
                "\n ..... X-Content-Type-Options = " + contentTypeOptions +
                "\n 11a. TLSv1.0 Supported = " + tlsv1Supported +
                "\n 11b. SSLv3.0 Supported = " + sslv3Supported +
                "\n 11c. SSLv2.0 Supported = " + sslv2Supported +
                "\n 12. Weak Cipher Supported = " + weakcipherSupported +
                "\n 13. Mysql available = " + mysqlAvailable +
                "\n 14. Mysql default password = " + defaultMysqlPassword +
                "\n 15. Didn't check script integrity = " + integrityCheck +
                "\n ..... Script URLs: = " + badScriptSrc.toString() +
                "\n 16. HTTP Trace = " + httpTrackEnabled +
                "\n ..... HTTP Trace Response code: = " + httpTrackResCode +
                "\n 17. Browsable Dir Enabled = " + browsableDirEnabled;
    }
}
