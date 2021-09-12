package test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface Mapper {

  @Results(id = "fruitResultMap", value = {
    @Result(property = "id", column = "ID"),
    @Result(property = "name", column = "NAME"),
    @Result(property = "qty", column = "QTY")
  })
  @Select("select * from fruit")
  List<Fruit> getFruits();

  @Results(id = "animalResultMap", value = {
    @Result(property = "type", column = "TYPE"),
    @Result(property = "num", column = "NUM"),
    @Result(property = "loc", column = "LOC")
  })
  @Select("select * from animal")
  List<Animal> getAnimals();

  @Select("{call test_proc(#{p1,mode=IN,jdbcType=VARCHAR},#{p2,mode=IN,jdbcType=VARCHAR})}")
  @Options(statementType = StatementType.CALLABLE)
  @ResultMap("fruitResultMap,animalResultMap")
  List<List<?>> execProc(Map<String, String> params);
}
