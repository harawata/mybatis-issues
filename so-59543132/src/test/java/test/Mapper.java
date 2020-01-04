package test;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface Mapper {

  @Select("{call get_person_pet(#{personId})}")
  @Options(statementType = StatementType.CALLABLE, resultSets = "personRS,petRS,petNameRS")
  @ResultMap("personRM")
  Person getPersonPet(Integer personId);

  @Select("{call get_all_person_pet()}")
  @Options(statementType = StatementType.CALLABLE, resultSets = "personRS,petRS,petNameRS")
  @ResultMap("personRM")
  List<Person> getAllPersonPet();

}
