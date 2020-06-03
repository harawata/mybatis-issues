package test;

public class Employee {
  private Integer emplId;
  private String name;
  private Addresses addressList;

  public Integer getEmplId() {
    return emplId;
  }

  public void setEmplId(Integer emplId) {
    this.emplId = emplId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Addresses getAddressList() {
    return addressList;
  }

  public void setAddressList(Addresses addressList) {
    this.addressList = addressList;
  }
}
