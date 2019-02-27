package com.pci.checker.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CertService {

    //  Table:
    //  CREATE TABLE `pci_checker`.`certificate_info` ( `id` INT NOT NULL AUTO_INCREMENT , `domain_name` VARCHAR(100) NOT NULL , `cert` TEXT NOT NULL , PRIMARY KEY (`id`), UNIQUE (`domain_name`)) ENGINE = InnoDB;

    public void crawlAndStoreCert(String domainName) throws Exception {

        String cert = crawlCertificate(domainName);
        storeCert(domainName, cert);


    }

    private String crawlCertificate(String domainName) throws Exception {

        ProcessBuilder pb = new
                ProcessBuilder("/bin/sh", "-c",
                String.format("openssl s_client -showcerts -connect %s:443 </dev/null 2>/dev/null | openssl x509 -outform PEM", domainName));

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

    private void storeCert(String domainName, String cert) throws Exception {
        String myDriver = "com.mysql.cj.jdbc.Driver";
        String myUrl = "jdbc:mysql://localhost/pci_checker";


        Class.forName(myDriver);

        try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
            String query = "insert into certificate_info (domain_name, cert) values (?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, domainName);
            preparedStmt.setString(2, cert);
            preparedStmt.execute();
            preparedStmt.close();
        }
    }

    public String fetchCertFromDB(String domainName) throws Exception {

        String myDriver = "com.mysql.cj.jdbc.Driver";
        String myUrl = "jdbc:mysql://localhost/pci_checker";


        Class.forName(myDriver);

        try (Connection conn = DriverManager.getConnection(myUrl, "root", "")) {
            String query = "SELECT cert FROM certificate_info where domain_name = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, domainName);
            ResultSet rs = preparedStmt.executeQuery();

            String cert = null;

            if (rs.next()) {
                cert = rs.getString("cert");
            }

            rs.close();
            preparedStmt.close();

            return cert;
        }
    }
}
