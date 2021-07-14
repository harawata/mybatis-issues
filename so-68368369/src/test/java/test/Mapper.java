package test;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface Mapper {

  @Select("select * from users")
  List<User> getUsers();

  @Options(statementType = StatementType.CALLABLE)
  @Insert("{call testproc(#{names,mode=IN,jdbcType=ARRAY,typeHandler=org.apache.ibatis.type.ArrayTypeHandler})}")
  void callTestproc(List<String> names);

}
