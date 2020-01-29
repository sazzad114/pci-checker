# PciCheckerLite: Source Code

A tool to detect the following 17 PCI related vulnerabilities in a website.


Test cases                          |   Severity
------------------------------------| -------------
(1) Mysql port (3306) detection     | Medium
(2) OpenSSH available               | Low
(3) Default Mysql user/password     | High
(4) Sensitive info over HTTP        | High
(5) Self-signed cert presented      | High
(6) Weak Cipher Supported           | High
(7) Expired cert presented          | Medium
(8) Wrong hostname in cert          | Medium
(9) Insecure Modulus                | High
(10) Weak hash in cert              | High
(11) TLSv1.0 Supported              | Low
(12) OpenSSH vulnerable             | High
(13) Missing script integrity check | Medium
(14) Server Info available          | Medium
(15) Browsable Dir Enabled          | High
(16) HTTP TRACE supported           | High
(17) Security Headers missing       | Medium


### Prerequisites to use PciCheckerLite:

1. Download and Install JDK 8 or above.
2. Set `JAVA_HOME` environment variable.
3. Download and Install Gradle.
4. Run `cd /path/to/pci-checker`
5. Run `gradle clean build`
6. Update domains.csv with the domains of your interest
7. Then run the following command.

    `cd /path/to/pci-checker && java -jar build/libs/pci-checker.jar "/path/to/domains.csv"`
    
    `domains.csv` should look like the following (a sample is provided under `data` directory),
    
    ```
    1,google.com
    2,youtube.com
    ```


## Disclaimer

##### PciCheckerLite is a research prototype under The MIT Licence. This tool is open-sourced to promote scientific comparison and progress. Authors of the PciCheckerLite  are not responsible for any abuse of the tool.

 Copyright 2019 Sazzadur Rahaman
 
 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
## Reference

If you find this project useful, please cite our CCS'19 [Security Certification in Payment Card Industry](https://dl.acm.org/citation.cfm?doid=3319535.3363195) paper.
