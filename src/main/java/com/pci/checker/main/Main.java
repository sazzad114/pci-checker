package com.pci.checker.main;

import com.pci.checker.service.AnalysisService;
import com.pci.checker.service.CertService;
import com.pci.checker.util.Utils;

public class Main {

    private static final String FILE = "/home/krishnokoli/projects/pci-checker/data/top.csv";

    private static CertService certService = new CertService();

    private static AnalysisService analysisService = new AnalysisService();

    public static void main(String[] args) throws Exception {

//        System.out.println(Utils.readLinksFromCsv(FILE));
//
//        certService.crawlAndStoreCert("facebook.com");
//
//        String cert = certService.fetchCertFromDB("facebook.com");
//
//        CertAnalaysisResult certAnalaysisResult = analysisService.analyzeCert("facebook.com", cert);

//        System.out.println(certAnalaysisResult);

//        System.out.println(Utils.getRedirectedUrl("amd.com"));
//      System.out.println(Utils.getOpenSshVersion("sazzadur.com"));

      Utils.getHeaders("abc.com");

    }
}
