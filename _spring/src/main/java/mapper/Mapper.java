package mapper;

import test.User;

public interface Mapper {
  User getUser(Integer id);

  int insertUser(User user);
}
