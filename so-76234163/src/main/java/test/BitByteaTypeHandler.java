
package test;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BitByteaTypeHandler extends BaseTypeHandler<byte[]>{

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, byte[] parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setObject(i, new String(parameter), Types.OTHER);
  }

  @Override
  public byte[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return rs.getBytes(columnName);
  }

  @Override
  public byte[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return rs.getBytes(columnIndex);
  }

  @Override
  public byte[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return cs.getBytes(columnIndex);
  }

}
