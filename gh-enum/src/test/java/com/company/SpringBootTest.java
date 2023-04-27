package com.company;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.company.Application;
import com.company.mapper.UserMapper;
import com.company.model.User;
import com.company.model.UserStatus;

import java.util.List;

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
    user.setStatus(UserStatus.ACTIVE);
    assertEquals(1, userMapper.insertUser(user));
  }

  @Test
  void shouldBeUsers() {
    List<User> users = userMapper.findAll();
    assertTrue(users.size() > 0);
  }

}
