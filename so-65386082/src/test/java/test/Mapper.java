package test;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface Mapper {

  @Select("select count(*) from tb_a")
  int count();

  @Delete("<script>DELETE FROM tb_a WHERE "
      + "<if test=\"context.name().equals('ALBUM')\">"
      + " album_id = #{id}"
      + "</if>"
      + "<if test=\"context.name().equals('VIDEOLIBRARY')\">"
      + " videolibrary_id = #{id}"
      + "</if>"
      + "</script>")
  boolean delete(@Param("id") int id, @Param("context") EContext context);
}
