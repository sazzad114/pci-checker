package com.pci.checker.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {

    public static List<String> readLinksFromCsv(String csvFile) throws Exception {

        List<String> links = new ArrayList<>();

        String line;

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            if (!line.isEmpty()) {
                links.add(line.split(",")[1].trim().toLowerCase());
            }
        }

        System.out.println("Total: " + links.size());

        return links;
    }

    public static String getRedirectedUrl(String domainName) {

        try {

            URL obj = new URL("http://" + domainName);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.addRequestProperty("User-Agent", "Mozilla");

            boolean redirect = false;

            // normally, 3xx is redirect
            int status = conn.getResponseCode();

            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                    || status == HttpURLConnection.HTTP_MOVED_PERM
                    || status == HttpURLConnection.HTTP_SEE_OTHER) {
                redirect = true;
            }

            String newUrl = "http://" + domainName;


            while (redirect) {

                // get redirect url from "location" header field
                newUrl = conn.getHeaderField("Location");

                if (newUrl.startsWith("https://")) {
                    break;
                }

                // get the cookie if need, for login
                String cookies = conn.getHeaderField("Set-Cookie");

                // open the new connection again

                conn = (HttpURLConnection) new URL(newUrl).openConnection();
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("Cookie", cookies);
                conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");

                // normally, 3xx is redirect
                status = conn.getResponseCode();

                if (status != HttpURLConnection.HTTP_MOVED_TEMP
                        && status != HttpURLConnection.HTTP_MOVED_PERM
                        && status != HttpURLConnection.HTTP_SEE_OTHER) {
                    break;
                }

                redirect = true;
            }

            return newUrl;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String getOpenSshVersion(String domainName) throws Exception {
        ProcessBuilder pb = new
                ProcessBuilder("/bin/sh", "-c",
                String.format("nc %s 22 -w 1", domainName));

        final Process p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder sshSoftwareSpec = new StringBuilder();

        String line;

        while ((line = br.readLine()) != null) {
            sshSoftwareSpec.append(line)
                    .append(' ');
        }

        return sshSoftwareSpec.toString();
    }

    public static Map<String, List<String>> getHeaders(String domainName) throws Exception {

        URL obj = new URL("https://" + domainName);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
        conn.addRequestProperty("User-Agent", "Mozilla");

        return conn.getHeaderFields();
    }


    public static int isHttpTrackEnabled(String domainName) throws Exception {

        URL obj = new URL("http://" + domainName);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("TRACE");
        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
        conn.addRequestProperty("User-Agent", "Mozilla");

        return conn.getResponseCode();
    }

    public static boolean isTls1Supported(String domainName, String version) throws Exception {

        ProcessBuilder pb = new
                ProcessBuilder("/bin/sh", "-c",
                String.format("timeout 2 openssl s_client -connect %s:443 -servername %s %s </dev/null 2>/dev/null", domainName, domainName, version));

        final Process p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder sshSoftwareSpec = new StringBuilder();

        String line;

        while ((line = br.readLine()) != null) {
            sshSoftwareSpec.append(line)
                    .append('\n');
        }

        String output = sshSoftwareSpec.toString();

        return !(output.isEmpty() || output.contains("no peer certificate available"));
    }

    public static boolean isWeakCipherSupported(String domainName) throws Exception {

        ProcessBuilder pb = new
                ProcessBuilder("/bin/sh", "-c",
                String.format("timeout 2 openssl s_client -connect %s:443 -servername %s -cipher 'IDEA:DES:MD5' </dev/null 2>/dev/null", domainName, domainName));

        final Process p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder sshSoftwareSpec = new StringBuilder();

        String line;

        while ((line = br.readLine()) != null) {
            sshSoftwareSpec.append(line)
                    .append('\n');
        }

        String output = sshSoftwareSpec.toString();

        return !(output.isEmpty() || output.contains("no peer certificate available"));
    }


    public static String getMysqlVersion(String domainName) throws Exception {

        ProcessBuilder pb = new
                ProcessBuilder("/bin/sh", "-c",
                String.format("nc %s 3306 -w 1", domainName));

        final Process p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder mysqlConnMsg = new StringBuilder();

        String line;

        while ((line = br.readLine()) != null) {
            mysqlConnMsg.append(line)
                    .append('\n');
        }

        return mysqlConnMsg.toString();
    }

    public static boolean isMysqlAccessible(String domainName) throws Exception {

        ProcessBuilder pb = new
                ProcessBuilder("/bin/sh", "-c",
                String.format("timeout 2 /opt/lampp/bin/mysql -u root -h %s", domainName));

        final Process p = pb.start();

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        StringBuilder mysqlError = new StringBuilder();

        String line;

        while ((line = br.readLine()) != null) {
            mysqlError.append(line)
                    .append(' ');
        }

        String output = mysqlError.toString();

        return output.trim().isEmpty();
    }

    public static boolean missesIntegrityChecked(String domainName, List<String> urlList) throws Exception {

        URL obj = new URL(domainName);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
        conn.addRequestProperty("User-Agent", "Mozilla");

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        String pattern = ".*src=[\"\']([^\"\']+)[\"\'].*";
        Pattern r = Pattern.compile(pattern);

        boolean missesCheck = false;

        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            if (inputLine.contains("<script")) {
                Matcher m = r.matcher(inputLine);
                if (m.matches()) {
                    String link = m.group(1);
                    urlList.add(link);

                    if (link.startsWith("//") || link.startsWith("http://") || link.startsWith("https://")) {
                        if (!inputLine.contains("integrity=")) {
                            missesCheck = true;
                        }
                    }
                }
            }
        }

        br.close();

        return missesCheck;
    }

    public static boolean isBrowseDirEnabled(String domainName, List<String> urlList) throws Exception {

        List<String> filteredList = urlList.stream()
                .filter(url -> !url.isEmpty() && !url.startsWith("//") && !url.startsWith("http://") && !url.startsWith("https://"))
                .collect(Collectors.toList());

        Map<String, Integer> dirCount = new LinkedHashMap<>();

        for (String link : filteredList) {
            String[] dirs = link.split("/");

            ArrayList dirList = new ArrayList();

            StringBuilder currDir = new StringBuilder();

            for (String dir : dirs) {
                currDir.append(dir).append("/");

                if (currDir.toString().equals("/")) {
                    continue;
                }

                Integer count = dirCount.putIfAbsent(currDir.toString(), 1);
                if (count != null) {
                    dirCount.put(currDir.toString(), count + 1);
                }
            }
        }

        if (!dirCount.isEmpty()) {

            for (String dir : dirCount.keySet()) {

                if (dirCount.get(dir) > 1) {

                    URL obj = new URL(domainName + "/" + dir);
                    HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(5000);
                    conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                    conn.addRequestProperty("User-Agent", "Mozilla");

                    if (conn.getResponseCode() != HttpURLConnection.HTTP_NOT_FOUND) {
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(conn.getInputStream()));

                        String inputLine;
                        while ((inputLine = br.readLine()) != null) {

                            if (inputLine.contains("<title>Index of")) {
                                if (inputLine.contains(dir + "</title>")) {
                                    return true;
                                }
                            }
                        }

                        br.close();
                    } else {
                        return false;
                    }
                }
            }
        }

        return false;
    }

    public static String getdomainNameFromUrl(String redirectURL) throws MalformedURLException {
        return new URL(redirectURL).getHost();
    }

    public static String unStaredDn(String subjectDn) {
        String toRet = subjectDn;

        if (subjectDn.startsWith("*")) {
            toRet = subjectDn.substring(1);
        }

        return toRet;
    }
}
