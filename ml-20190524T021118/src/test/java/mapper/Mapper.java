package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import component.User;

public interface Mapper {
  @Select("select * from users where id = #{id}")
  User getUser(Integer id);

  @Select("select * from users")
  List<User> getUsers();
}
