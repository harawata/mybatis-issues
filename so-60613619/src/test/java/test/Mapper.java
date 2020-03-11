package test;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface Mapper {
  @Select("SELECT id, name FROM t_service s")
  @Results(value = {
    @Result(column = "id", property = "id"),
    @Result(column = "name", property = "name"),
    @Result(property = "rates", column = "id", javaType = List.class, many = @Many(select = "getAllRates"))
  })
  List<Service> findAll();

  @Select("SELECT date_from, date_to, currency FROM t_rate where t_rate.service_id = #{serviceId}")
  @Results(value = {
    @Result(property = "dateFrom", column = "date_from"),
    @Result(property = "dateTo", column = "date_to"),
    @Result(property = "currency", column = "currency")
  })
  List<Rate> getAllRates(@Param("serviceId") UUID serviceId);
}
