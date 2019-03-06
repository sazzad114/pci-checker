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
