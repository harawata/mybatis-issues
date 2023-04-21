package test;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface Mapper {

  @Select("select userid id, name, age from users where userid = #{id}")
  User selectById(long id);

  void insert(@Param("genericRecordMap") GenericRecordMap param);

}
