package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import service.UserService;
import service.UserService2;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
class SpringTest {

  @Autowired
  private UserService userService;
  @Autowired
  private UserService2 userService2;

  @Test
  void batchSqlSessionTemplate() {
    List<User> users = List.of(new User(2, "User2"), new User(3, "User3"));
    List<BatchResult> results = userService.insertUsers(users);
    assertEquals(1, results.size());
    BatchResult result = results.get(0);
    assertEquals(1, result.getUpdateCounts()[0]);
    assertEquals(1, result.getUpdateCounts()[1]);
  }

  @Test
  void batchSqlSessionTemplateWithoutTransactional() {
    List<User> users = List.of(new User(2, "User2"), new User(3, "User3"));
    List<BatchResult> results = userService.insertUsersWithoutTransactional(users);
    assertEquals(1, results.size()); // FAIL
  }

  @Test
  void sqlSessionFactory() {
    List<User> users = List.of(new User(2, "User2"), new User(3, "User3"));
    List<BatchResult> results = userService2.insertUsers(users);
    assertEquals(1, results.size());
    BatchResult result = results.get(0);
    assertEquals(1, result.getUpdateCounts()[0]);
    assertEquals(1, result.getUpdateCounts()[1]);
  }

}
