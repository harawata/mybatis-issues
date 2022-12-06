package test;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;

public interface Mapper {

  StudentEntity getStudent(@Param("name") String name,
      @Param("auditTimestamp") Timestamp auditTimestamp);

}
