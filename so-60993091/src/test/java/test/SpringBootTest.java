package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.ibatis.executor.BatchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = Application.class)
public class SpringBootTest {
  @Autowired
  private UserService userService;

  // @Rollback(false)
  @Test
  public void update() {
    List<BatchResult> result = userService.updateUsers(initUsers());
    assertNotNull(result);
  }

  // @Rollback(false)
  @Test
  public void insertThenUpdate() {
    List<BatchResult> result = userService.insertUsers(initUsers());
    assertNotNull(result);
  }

  @Test
  public void demoGhSbs478() {
    List<User> users = new ArrayList<>();
    users.add(new User(251, "user 251"));
    users.add(new User(252, "user 252"));
    users.add(new User(253, "user 253"));
    users.add(new User(250, "user 250"));
    try {
      userService.insertUsers2(users);
      fail("should throw duplicate key exception");
    } catch (DuplicateKeyException e) {
      // expected
    }
    assertNotNull(userService.selectUser(250));
    assertNull(userService.selectUser(251));
    assertNull(userService.selectUser(252));
    assertNull(userService.selectUser(253));
  }

  private List<User> initUsers() {
    return IntStream.rangeClosed(1, 250).mapToObj(i -> new User(i, "user " + i)).collect(Collectors.toList());
  }
}
