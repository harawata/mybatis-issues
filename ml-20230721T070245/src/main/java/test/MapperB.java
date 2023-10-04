package test;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

@CacheNamespace
public interface MapperB {
  @Select("select * from pets where id = #{id}")
  Pet getPet(Integer id);
}
