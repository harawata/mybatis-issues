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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedJdbcTypes(value = { JdbcType.CHAR })
@MappedTypes({ Boolean.class, boolean.class })
public class YNBooleanTypeHandler extends BaseTypeHandler<Boolean> {
  private final Log log = LogFactory.getLog(YNBooleanTypeHandler.class);

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter,
      JdbcType jdbcType)
      throws SQLException {
    log.debug(getClass().getSimpleName() + " is invoked.");
    ps.setString(i, Boolean.TRUE.equals(parameter) ? "Y" : "N");
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, String columnName)
      throws SQLException {
    log.debug(getClass().getSimpleName() + " is invoked.");
    return "Y".equals(rs.getString(columnName));
  }

  @Override
  public Boolean getNullableResult(ResultSet rs, int columnIndex)
      throws SQLException {
    log.debug(getClass().getSimpleName() + " is invoked.");
    return "Y".equals(rs.getString(columnIndex));
  }

  @Override
  public Boolean getNullableResult(CallableStatement cs, int columnIndex)
      throws SQLException {
    log.debug(getClass().getSimpleName() + " is invoked.");
    return "Y".equals(cs.getString(columnIndex));
  }
}
