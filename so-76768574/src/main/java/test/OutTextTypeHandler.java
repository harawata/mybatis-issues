package test;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import oracle.jdbc.OracleArray;
import oracle.jdbc.OracleConnection;

public class OutTextTypeHandler extends BaseTypeHandler<OutText> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, OutText parameter, JdbcType jdbcType)
      throws SQLException {
    OracleConnection con = ps.getConnection().unwrap(OracleConnection.class);
    Array array = con.createOracleArray("ARR_TEXT_OUT", parameter.getOutText());
    try {
      ps.setArray(i, array);
    } finally {
      array.free();
    }
  }

  @Override
  public OutText getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return oracleArrayToOutText((OracleArray) rs.getArray(columnName));
  }

  @Override
  public OutText getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return oracleArrayToOutText((OracleArray) rs.getArray(columnIndex));
  }

  @Override
  public OutText getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return oracleArrayToOutText((OracleArray) cs.getArray(columnIndex));
  }

  private OutText oracleArrayToOutText(OracleArray array) throws SQLException {
    if (array == null) {
      return null;
    }
    OutText result = new OutText((String[]) array.getArray());
    array.free();
    return result;
  }
}
