package test;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class MacaddrTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setObject(i, parameter, Types.OTHER);
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
    // The built-in StringTypeHandler may be used.
    return rs.getString(columnName);
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    // The built-in StringTypeHandler may be used.
    return rs.getString(columnIndex);
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    // The built-in StringTypeHandler may be used.
    return cs.getString(columnIndex);
  }

}
