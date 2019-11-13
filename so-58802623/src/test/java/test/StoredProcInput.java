package test;

import java.util.Date;

public class StoredProcInput {
  private String SP_FirstName = "";
  private String SP_LastName = "";
  private String SP_Gender = "";
  private Date SP_Birthday = null;
  private Integer SP_Age = 0;

  public String getSP_FirstName() {
    return SP_FirstName;
  }

  public void setSP_FirstName(String sP_FirstName) {
    SP_FirstName = sP_FirstName;
  }

  public String getSP_LastName() {
    return SP_LastName;
  }

  public void setSP_LastName(String sP_LastName) {
    SP_LastName = sP_LastName;
  }

  public String getSP_Gender() {
    return SP_Gender;
  }

  public void setSP_Gender(String sP_Gender) {
    SP_Gender = sP_Gender;
  }

  public Date getSP_Birthday() {
    return SP_Birthday;
  }

  public void setSP_Birthday(Date sP_Birthday) {
    SP_Birthday = sP_Birthday;
  }

  public Integer getSP_Age() {
    return SP_Age;
  }

  public void setSP_Age(Integer sP_Age) {
    SP_Age = sP_Age;
  }
}
