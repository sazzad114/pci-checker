package com.pci.checker.main;

import com.pci.checker.service.AnalysisService;
import com.pci.checker.util.Utils;

public class Main {

    private static AnalysisService analysisService = new AnalysisService();

    public static void main(String[] args) throws Exception {

        String source = null;

        if (args.length == 1) {
            source = args[0];
        } else {
            System.err.println("Please run the following command:\n java -jar /path/to/pci-checker.jar \"/path/to/domains.csv\"");
        }

        int curr = 1;
        System.out.println("=====================================");
        for (String domainName : Utils.readLinksFromCsv(source)) {
            try {
                System.out.println("*** Analyzing Domain: [" + curr + " : " + domainName + "] " + analysisService.getAnalysisResult(domainName));
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Problem processing domain: " + domainName);
            }
            System.out.println("=====================================");
            curr += 1;
        }
    }
}
