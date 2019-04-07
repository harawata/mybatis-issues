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

public class VestingDateWiseData {
  private String vestingDateId;
  private String awardDate;
  private String investmentDate;
  private String vestingDate;
  private Double amount;
  private Double returnAmount;
  private Double vestingAmount;

  public String getVestingDateId() {
    return vestingDateId;
  }

  public void setVestingDateId(String vestingDateId) {
    this.vestingDateId = vestingDateId;
  }

  public String getAwardDate() {
    return awardDate;
  }

  public void setAwardDate(String awardDate) {
    this.awardDate = awardDate;
  }

  public String getInvestmentDate() {
    return investmentDate;
  }

  public void setInvestmentDate(String investmentDate) {
    this.investmentDate = investmentDate;
  }

  public String getVestingDate() {
    return vestingDate;
  }

  public void setVestingDate(String vestingDate) {
    this.vestingDate = vestingDate;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Double getReturnAmount() {
    return returnAmount;
  }

  public void setReturnAmount(Double returnAmount) {
    this.returnAmount = returnAmount;
  }

  public Double getVestingAmount() {
    return vestingAmount;
  }

  public void setVestingAmount(Double vestingAmount) {
    this.vestingAmount = vestingAmount;
  }
}
