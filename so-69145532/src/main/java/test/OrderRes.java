package test;

public class OrderRes {

  private Long id;

  private Long mainId;

  private List<FromRes> fromResList;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getMainId() {
    return mainId;
  }

  public void setMainId(Long mainId) {
    this.mainId = mainId;
  }

  public List<FromRes> getFromResList() {
    return fromResList;
  }

  public void setFromResList(List<FromRes> fromResList) {
    this.fromResList = fromResList;
  }
}
