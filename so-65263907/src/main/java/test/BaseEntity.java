package test;

public class BaseEntity {
  private String name;
  private String[] filename;
  private String[] outparam;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getFilename() {
    return filename;
  }

  public void setFilename(String[] filename) {
    this.filename = filename;
  }

  public String[] getOutparam() {
    return outparam;
  }

  public void setOutparam(String[] outparam) {
    this.outparam = outparam;
  }
}
