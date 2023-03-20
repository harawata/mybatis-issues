package test;

import lombok.Data;

@Data
public class MyUserDTO {
  private final Long id;
  private final String name;
  private final Integer age;
  private final String address;
}
