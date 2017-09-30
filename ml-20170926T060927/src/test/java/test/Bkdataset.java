package test;

public class Bkdataset {
  private String acpicd;

  private String alttype;

  private String altdate;

  /**
   * @param acpicd
   * @param alttype
   * @param altdate
   */
  public Bkdataset(String acpicd, String alttype, String altdate) {
    super();
    this.acpicd = acpicd;
    this.alttype = alttype;
    this.altdate = altdate;
  }

  public String getAcpicd() {
    return acpicd;
  }

  public void setAcpicd(String acpicd) {
    this.acpicd = acpicd;
  }

  public String getAlttype() {
    return alttype;
  }

  public void setAlttype(String alttype) {
    this.alttype = alttype;
  }

  public String getAltdate() {
    return altdate;
  }

  public void setAltdate(String altdate) {
    this.altdate = altdate;
  }
}
