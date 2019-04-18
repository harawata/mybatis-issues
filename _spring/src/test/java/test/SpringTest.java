package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import mapper.Mapper;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringTest {

  @Autowired
  private Mapper mapper;

  @Test
  public void shouldGetAUser() {
    User user = mapper.getUser(1);
    Assert.assertEquals("User1", user.getName());
  }

  @Test
  public void shouldInsertAUser() {
    User user = new User();
    user.setId(2);
    user.setName("User2");
    mapper.insertUser(user);
  }

}
