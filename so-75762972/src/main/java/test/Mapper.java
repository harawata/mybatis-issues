package test;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface Mapper {

  @Select("SELECT * FROM user_table WHERE id = #{id}")
  MyUserDTO findByUserId(@Param("id") Long id);

}
