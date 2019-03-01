package com.pci.checker.util;

import com.sun.deploy.net.HttpResponse;
import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static List<String> readLinksFromCsv(String csvFile) throws Exception {

        List<String> links = new ArrayList<>();

        String line;

        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {

            links.add(line.split(",")[1]);
        }

        return links;
    }

    public static String getRedirectedUrl(String domainName) {

        try {

            URL obj = new URL("http://" + domainName);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(1000);
            conn.setConnectTimeout(1000);
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
                conn.setReadTimeout(1000);
                conn.setConnectTimeout(1000);
                conn.setRequestProperty("Cookie", cookies);
                conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");

                System.out.println("Redirect to URL : " + newUrl);

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
                    .append('\n');
        }

        return sshSoftwareSpec.toString();
    }

    public static void getHeaders(String domainName) {

        try {

            URL obj = new URL("https://" + domainName);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(1000);
            conn.setConnectTimeout(1000);
            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.addRequestProperty("User-Agent", "Mozilla");

            Map<String, List<String>> map = conn.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey() +
                        " ,Value : " + entry.getValue());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
