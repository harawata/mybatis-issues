package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mapper.UserMapper;

@ExtendWith(SpringExtension.class)
@org.springframework.boot.test.context.SpringBootTest(classes = Application.class)
class SpringBootTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  void shouldGetAUser() {
    User user = userMapper.getUser(1);
    assertEquals("User1", user.getName());
  }

  @Test
  void shouldInsertAUser() {
    User user = new User();
    user.setId(2);
    user.setName("User2");
    assertEquals(1, userMapper.insertUser(user));
  }

}
