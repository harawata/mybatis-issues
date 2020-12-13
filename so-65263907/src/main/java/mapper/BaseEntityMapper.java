package mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import test.BaseEntity;

public interface BaseEntityMapper {
  void add(BaseEntity baseEntity);

  @Select("select name, filename from test order by filename")
  List<Map<String, Object>> selectTest();
}
