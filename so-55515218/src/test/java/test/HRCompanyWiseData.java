/**
 *    Copyright 2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package test;

public class HRCompanyWiseData {
  private String companyId;
  private String companyCode;
  private PeriodWiseData nonMidYear;
  private PeriodWiseData midYear;

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getCompanyCode() {
    return companyCode;
  }

  public void setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
  }

  public PeriodWiseData getNonMidYear() {
    return nonMidYear;
  }

  public void setNonMidYear(PeriodWiseData nonMidYear) {
    this.nonMidYear = nonMidYear;
  }

  public PeriodWiseData getMidYear() {
    return midYear;
  }

  public void setMidYear(PeriodWiseData midYear) {
    this.midYear = midYear;
  }
}
