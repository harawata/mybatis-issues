package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import mapper1.UserMapper;
import mapper2.GroupMapper;
import test.config.MyBatisConfig1;
import test.config.MyBatisConfig2;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = {
  Application.class, MyBatisConfig1.class, MyBatisConfig2.class })
public class SpringBootTest {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private GroupMapper groupMapper;

  @Test
  public void shouldGetAUser() {
    User user = userMapper.getUser(1);
    assertEquals("User1", user.getName());
    assertEquals("Additional info 1", user.getAdditionalInfo());
  }

  @Test
  public void shouldInsertAUser() {
    User user = new User();
    user.setId(3);
    user.setName("User3");
    userMapper.insertUser(user);

    user = userMapper.getUser(3);
    assertEquals("User3", user.getName());
  }

  @Test
  public void shouldGetAGroup() {
    Group group = groupMapper.getGroup(1);
    assertEquals("Group1", group.getName());
    assertEquals("Additional info 1", group.getAdditionalInfo());
  }

  @Test
  public void shouldInsertAGroup() {
    Group group = new Group();
    group.setId(3);
    group.setName("Group3");
    groupMapper.insertGroup(group);

    group = groupMapper.getGroup(3);
    assertEquals("Group3", group.getName());
  }
}
