# PciCheckerLite: Source Code

An tool to detect 17 PCI related vulnerabilities.

### Prerequisites to use PciCheckerLite:

1. Download and Install JDK 8 or above.
2. Set `JAVA7_HOME` environment variable.
3. Download and Install Gradle.
4. Run `cd /path/to/pci-checker`
5. Run `gradle clean build`
6. Update domains.csv with the domains of your interest
7. Then run the following command.

`cd /path/to/pci-checker && java -jar build/libs/pci-checker.jar "/path/to/domains.csv"`

## Disclaimer

##### PciCheckerLite is a research prototype under The MIT Licence. This tool is open-sourced to promote scientific comparison and progress. Authors of the PciCheckerLite  are not responsible for any abuse of the tool.

 Copyright 2019 Sazzadur Rahaman
 
 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
## Reference

If you find this project useful, please cite our CCS'19 [Security Certification in Payment Card Industry](https://dl.acm.org/citation.cfm?doid=3319535.3363195) paper.
