/**
 *    Copyright 2009-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package test;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface Mapper {
  @Results({
      @Result(id = true, column = "id", property = "id"),
      @Result(column = "yn", property = "yn"),
      @Result(column = "onezero", property = "onezero")
  })
  @Select("select * from bools where id = #{id}")
  Bools getBools(Integer id);

  @Insert({
      "insert into bools (id, yn, onezero) values",
      "(#{id}, #{yn, jdbcType=CHAR}, #{onezero, jdbcType=INTEGER})"
  })
  int insertBools(Bools bools);
}
