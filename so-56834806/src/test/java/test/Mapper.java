package test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface Mapper {

  void saveUsers(@Param("singleUser") User singleUser, @Param("users") List<User> users,
      @Param("namePrefix") String namePrefix,
      @Param("outParam") Map<String, ?> outParam);

  @Select("select * from users order by id")
  List<User> selectUsers();

}
