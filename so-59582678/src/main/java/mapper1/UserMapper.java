package mapper1;

import org.apache.ibatis.annotations.Mapper;

import test.User;

@Mapper
public interface UserMapper {
  User getUser(Integer id);

  void insertUser(User user);
}
