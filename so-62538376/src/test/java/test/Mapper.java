package test;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface Mapper {
  @Select("select count(*) from users")
  int countUsers();

  @Insert({
    "insert into users (id, name1, name2, name3, name4, name5, name6, name7,",
    "name8, name9, name10, name11, name12, name13, name14) values ",
    "(#{id}, #{name1}, #{name2}, #{name3}, #{name4}, #{name5}, #{name6}, #{name7},",
    " #{name8}, #{name9}, #{name10}, #{name11}, #{name12}, #{name13}, #{name14})" })
  void insertUser(User user);
}
