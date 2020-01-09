package test;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface Mapper {
  @Select("select * from users where id = #{id}")
  User getUser(Integer id);

  @Insert("insert into users (name) values (#{name})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void insertUser(User user);
}
