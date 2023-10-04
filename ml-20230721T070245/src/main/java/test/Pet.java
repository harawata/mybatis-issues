package test;

import java.io.Serializable;

public class Pet implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private Integer userId;
  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
