package com.pci.checker.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
}
