/**
 *    Copyright 2009-2015 the original author or authors.
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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.alibaba.fastjson.JSON;

@MappedTypes({ Pet.class, Car.class })
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {

  private Class<T> type;

  public JsonTypeHandler(Class<T> type) {
    if (type == null) {
      throw new IllegalArgumentException("Type argument cannot be null");
    }
    this.type = type;
  }

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
    ps.setString(i, JSON.toJSONString(parameter));
  }

  @Override
  public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String value = rs.getString(columnName);
    if (value != null)
      return JSON.parseObject(value, type);
    return null;
  }

  @Override
  public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String value = rs.getString(columnIndex);
    if (value != null)
      return JSON.parseObject(value, type);
    return null;
  }

  @Override
  public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String value = cs.getString(columnIndex);
    if (value != null)
      return JSON.parseObject(value, type);
    return null;
  }
}
