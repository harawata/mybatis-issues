package test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mapper.UserMapper;

@SpringBootApplication
@MapperScan("mapper")
public class Application implements CommandLineRunner {

  @Autowired
  private UserMapper userMapper;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    User user = new User();
    user.setName("User3");
    userMapper.insertUser(user);
    System.out.println("Auto-generated ID is " + user.getId());
  }
}
