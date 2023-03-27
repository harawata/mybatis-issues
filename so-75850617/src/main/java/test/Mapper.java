package test;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface Mapper {
  @Select("SELECT * FROM event WHERE id=#{id}")
  Event get(final int id);

  @Insert({"INSERT INTO",
      "event(time, duration, summary_id)",
      "VALUES(#{e.time}, #{e.duration}, #{summaryId})"})
  @Options(useGeneratedKeys=true, keyProperty = "result.id")
  void insert(@Param("e") final Event item, @Param("summaryId") final int summaryId, @Param("result") final IdentifierResult result);
}
