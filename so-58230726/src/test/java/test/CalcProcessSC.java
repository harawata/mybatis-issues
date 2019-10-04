package test;

import java.time.LocalDate;

public class CalcProcessSC {
  private LocalDate asOfDate;
  private String user;
  private Integer processId;
  private Integer output;

  public LocalDate getAsOfDate() {
    return asOfDate;
  }

  public void setAsOfDate(LocalDate asOfDate) {
    this.asOfDate = asOfDate;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Integer getProcessId() {
    return processId;
  }

  public void setProcessId(Integer processId) {
    this.processId = processId;
  }

  public Integer getOutput() {
    return output;
  }

  public void setOutput(Integer output) {
    this.output = output;
  }
}
