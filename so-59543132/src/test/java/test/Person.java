package test;

import java.util.List;

public class Person {
  private Integer id;
  private String name;
  private List<Pet> pets;
  private List<String> petNames;

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

  public List<Pet> getPets() {
    return pets;
  }

  public void setPets(List<Pet> pets) {
    this.pets = pets;
  }

  public List<String> getPetNames() {
    return petNames;
  }

  public void setPetNames(List<String> petNames) {
    this.petNames = petNames;
  }
}
