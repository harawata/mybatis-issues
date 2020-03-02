package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import mapper.UserMapper;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = Application.class)
public class SpringBootTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  public void shouldGetAUser() {
    User user = userMapper.getUser(3);
    assertEquals("User3", user.getName());
  }

}
