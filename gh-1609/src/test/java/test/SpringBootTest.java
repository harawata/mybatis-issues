package test;

import static org.junit.Assert.*;

import java.util.UUID;

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
  public void shouldInsertAUser() {
    UUID uuid = UUID.randomUUID();
    User user = new User();
    user.setId(2);
    user.setName("User2");
    user.setGuid(uuid);
    userMapper.insertUser(user);

    user = userMapper.getUser(2, uuid);
    assertEquals(uuid, user.getGuid());
  }

}
