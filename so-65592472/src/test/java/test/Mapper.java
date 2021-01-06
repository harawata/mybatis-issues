package test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface Mapper {
  @Select("select * from users order by id")
  List<User> getUsers();

  int updateNames(Map<String, Object> params);
}
