package test;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface Mapper {
  @Select("select * from users where id = #{id}")
  User getUser(Integer id);

  @Insert("insert into users (id, name) values (#{id}, #{name})")
  void insertUser(User user);
}
