package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import test.User;

public interface UserMapper {
  User getUser(Integer id);

  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  @Insert("insert into users values(#{id}, #{name})")
  void insertUser(User user);
}
