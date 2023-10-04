package test;

import org.apache.ibatis.annotations.Update;

public interface MapperA {
  User getUser(Integer id);

  @Update("update pets set name = #{name}, user_id = #{userId} where id = #{id}")
  int updatePet(Pet pet);
}
