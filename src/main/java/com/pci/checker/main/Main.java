package com.pci.checker.main;

import com.pci.checker.service.AnalysisService;
import com.pci.checker.util.Utils;

public class Main {

    private static final String FILE = "/home/krishnokoli/projects/pci-checker/data/top.csv";

    private static AnalysisService analysisService = new AnalysisService();

    public static void main(String[] args) throws Exception {

        System.out.println("=====================================");
        for (String domainName : Utils.readLinksFromCsv(FILE)) {
            System.out.println("*** Analyzing Domain: " + domainName);
            System.out.println(analysisService.getAnalysisResult(domainName));
            System.out.println("=====================================");
        }


    }
}
