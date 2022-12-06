package test;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mapper.Mapper;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
class SpringTest {

  @Autowired
  private Mapper mapper;

  @Test
  void shouldGetAUser() {
    User user = mapper.getUser(1);
    assertEquals("User1", user.getName());
  }

  @Test
  void shouldInsertAUser() {
    User user = new User();
    user.setId(2);
    user.setName("User2");
    assertEquals(1, mapper.insertUser(user));
  }

}
