package com.pci.checker.main;

import com.pci.checker.service.AnalysisService;
import com.pci.checker.util.Utils;

public class Main {

    private static final String FILE = "/home/krishnokoli/projects/pci-checker/data/top1.csv";

    private static AnalysisService analysisService = new AnalysisService();

    public static void main(String[] args) throws Exception {

        int curr = 1;
        System.out.println("=====================================");
        for (String domainName : Utils.readLinksFromCsv(FILE)) {
            System.out.println("*** Analyzing Domain: [" + curr + "] " + domainName);
            try {
                System.out.println(analysisService.getAnalysisResult(domainName));
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Problem processing domain: " + domainName);
            }
            System.out.println("=====================================");
            curr += 1;
        }
    }
}
