package test;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import oracle.jdbc.driver.OracleConnection;

public class UserListTypeHandler extends BaseTypeHandler<List<User>> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<User> parameter, JdbcType jdbcType)
      throws SQLException {
    Connection conn = ps.getConnection();
    List<Struct> structs = new ArrayList<Struct>();
    for (int idx = 0; idx < parameter.size(); idx++) {
      User user = parameter.get(idx);
      Object[] result = { user.getId(), user.getName() };
      structs.add(conn.createStruct("S_USER_OBJ", result));
    }
    Array array = ((OracleConnection) conn).createOracleArray("S_USER_OBJ_LIST", structs.toArray());
    ps.setArray(i, array);
    array.free();
  }

  @Override
  public List<User> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return null;
  }

  @Override
  public List<User> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return null;
  }

  @Override
  public List<User> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    List<User> result = new ArrayList<>();
    Array array = cs.getArray(columnIndex);
    Object[] objs = (Object[]) array.getArray();
    for (Object obj : objs) {
      Object[] attrs = ((Struct) obj).getAttributes();
      result.add(new User(((BigDecimal) attrs[0]).intValue(), (String) attrs[1]));
    }
    array.free();
    return result;
  }

}
