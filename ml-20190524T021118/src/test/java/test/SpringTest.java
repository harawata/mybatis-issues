package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import component.User;
import mapper.Mapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class SpringTest {

  @Autowired
  private Mapper mapper;

  @Test
  public void shouldGetAUser() {
    User user = mapper.getUser(1);
    assertEquals("User1", user.getName());
    assertNotNull(user.getDataSource());
  }

  @Test
  public void shouldGetUsers() {
    List<User> users = mapper.getUsers();
    assertEquals(2, users.size());
    assertEquals("User1", users.get(0).getName());
    assertNotNull(users.get(0).getDataSource());
    assertNotNull(users.get(1).getDataSource());
  }

}
