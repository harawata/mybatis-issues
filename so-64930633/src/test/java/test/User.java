package test;

import java.util.List;

public class User {
  private Integer id;
  private String name;
  private List<String> roles;

  public User(Integer id, String name, List<String> roles) {
    super();
    this.id = id;
    this.name = name;
    this.roles = roles;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<String> getRoles() {
    return roles;
  }
}
