package test;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class UserTypeHandler extends BaseTypeHandler<User> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, User user, JdbcType jdbcType)
      throws SQLException {
    Connection conn = ps.getConnection();
    Object[] result = { user.getId(), user.getName() };
    Struct struct = conn.createStruct("S_USER_OBJ", result);
    ps.setObject(i, struct);
  }

  @Override
  public User getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return null;
  }

  @Override
  public User getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return null;
  }

  @Override
  public User getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    Struct struct = (Struct) cs.getObject(columnIndex);
    Object[] attributes = struct.getAttributes();
    // Oracle driver returns BigDecimal for any numeric value.
    return new User(((BigDecimal)attributes[0]).intValue(), (String)attributes[1]);
  }
}
