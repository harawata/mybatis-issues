package mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import test.User;

public interface UserMapper {
  @Update("update users set name = #{name} where id = #{id}")
  void update(User user);

  @Update("create temporary table tmp (id int, name varchar(10))")
  void createTempTable();

  @Insert("insert into tmp (id, name) values (#{id}, #{name})")
  void insertTemp(User user);

  @Update({ "update users inner join tmp on tmp.id = users.id",
    "set users.name = tmp.name" })
  int updateUsers();
}
