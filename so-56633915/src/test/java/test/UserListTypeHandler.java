package test;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

public class UserListTypeHandler extends BaseTypeHandler<List<User>>{

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<User> parameter, JdbcType jdbcType)
      throws SQLException {
    SQLServerDataTable dataTable = new SQLServerDataTable();
    dataTable.addColumnMetadata("id", java.sql.Types.INTEGER);
    dataTable.addColumnMetadata("name", java.sql.Types.VARCHAR);
    for (User user : parameter) {
      dataTable.addRow(user.getId(), user.getName());
    }
    ps.unwrap(SQLServerPreparedStatement.class).setStructured(i, "UserTableType", dataTable);
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
    return null;
  }

}
