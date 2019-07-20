package test;

import java.util.UUID;

public class User {

  private Integer id;
  private String name;
  private UUID guid;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getGuid() {
    return guid;
  }

  public void setGuid(UUID guid) {
    this.guid = guid;
  }
}
