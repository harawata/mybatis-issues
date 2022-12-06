package mapper;

import test.User;

public interface UserMapper {
  User getUser(Integer id);

  int insertUser(User user);
}
