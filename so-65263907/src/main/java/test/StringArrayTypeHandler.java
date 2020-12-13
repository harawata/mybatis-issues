package test;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import oracle.jdbc.OracleConnection;

public class StringArrayTypeHandler extends BaseTypeHandler<String[]> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType)
      throws SQLException {
    OracleConnection conn = ps.getConnection().unwrap(OracleConnection.class);
    Array array = conn.createOracleArray("STRINGARRAY", parameter);
    ps.setArray(i, array);
    array.free();
  }

  @Override
  public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return convertArray(rs.getArray(columnName));
  }

  @Override
  public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return convertArray(rs.getArray(columnIndex));
  }

  @Override
  public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return convertArray(cs.getArray(columnIndex));
  }

  private String[] convertArray(Array array) throws SQLException {
    String[] result = (String[]) array.getArray();
    array.free();
    return result;
  }

}
