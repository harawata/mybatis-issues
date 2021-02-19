package test;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

public interface Mapper {

  @Insert("insert into user1 (id, name) values (test_seq.nextval, #{name})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void insert1(User user);

  @Insert("insert into user2 (name) values (#{name})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void insert2(User user);

  @Insert("insert into user3 (name) values (#{name})")
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  void insert3(User user);

  @Select("select * from user1 where id = #{id}")
  User select1(Integer id);

  @Select("select * from user2 where id = #{id}")
  User select2(Integer id);

  @Select("select * from user3 where id = #{id}")
  User select3(Integer id);
}
