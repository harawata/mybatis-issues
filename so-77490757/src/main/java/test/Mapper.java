package test;

import java.util.Map;

import org.apache.ibatis.annotations.Select;

public interface Mapper {

  int updateColumn1(Map<String, Long> param);

  @Select("select column_1 from table_a where id = #{id}")
  String selectColumn1(Integer id);

}
