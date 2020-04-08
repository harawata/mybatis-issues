package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.ibatis.executor.BatchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
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

  private List<User> initUsers() {
    return IntStream.rangeClosed(1, 250).mapToObj(i -> new User(i, "user " + i)).collect(Collectors.toList());
  }
}
