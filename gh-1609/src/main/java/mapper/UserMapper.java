package mapper;

import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import test.User;

public interface UserMapper {
  User getUser(@Param("id") Integer id, @Param("guid") UUID guid);

  void insertUser(User user);
}
