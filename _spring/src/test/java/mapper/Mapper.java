package mapper;

import test.User;

public interface Mapper {
  User getUser(Integer id);

  void insertUser(User user);
}
