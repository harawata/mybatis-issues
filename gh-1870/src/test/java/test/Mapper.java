package test;

import org.apache.ibatis.annotations.Select;

public interface Mapper {
  @Select("select 1; waitfor delay '00:00:03'")
  int selectSleep();

}
