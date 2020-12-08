package mapper;

import test.User;

public interface UserMapper {
  User getUser(Integer id);

  void insertUser(User user);
}
