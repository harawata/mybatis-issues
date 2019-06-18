package test;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface Mapper {

  @Select("select * from users order by id")
  List<User> selectUsers();

  @Options(statementType = StatementType.CALLABLE)
  @Insert("{call uspAddUsers(#{users,typeHandler=test.UserListTypeHandler})}")
  void insertUsers(@Param("users") List<User> users);

}
