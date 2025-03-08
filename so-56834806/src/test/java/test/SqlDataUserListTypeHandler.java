package test;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import oracle.jdbc.OracleConnection;

public class SqlDataUserListTypeHandler extends BaseTypeHandler<List<SqlDataUser>> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<SqlDataUser> parameter, JdbcType jdbcType)
      throws SQLException {
    OracleConnection conn = ps.getConnection().unwrap(OracleConnection.class);
    Array array = conn.createOracleArray("S_USER_OBJ_LIST", parameter.toArray());
    ps.setArray(i, array);
    array.free();
  }

  @Override
  public List<SqlDataUser> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return null;
  }

  @Override
  public List<SqlDataUser> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return null;
  }

  @Override
  public List<SqlDataUser> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    List<SqlDataUser> result = new ArrayList<>();
    Array array = cs.getArray(columnIndex);
    Object[] objs = (Object[]) array.getArray();
    for (Object obj : objs) {
      result.add((SqlDataUser) obj);
    }
    array.free();
    return result;
  }

}
