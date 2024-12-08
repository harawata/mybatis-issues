package test;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class MonthlyCountsTypeHandler implements TypeHandler<Map<String, Integer>> {

  @Override
  public void setParameter(PreparedStatement ps, int i, Map<String, Integer> parameter, JdbcType jdbcType)
      throws SQLException {
    // not used
  }

  @Override
  public Map<String, Integer> getResult(ResultSet rs, String columnName) throws SQLException {
    return parse(rs.getString(columnName));
  }

  @Override
  public Map<String, Integer> getResult(ResultSet rs, int columnIndex) throws SQLException {
    return parse(rs.getString(columnIndex));
  }

  @Override
  public Map<String, Integer> getResult(CallableStatement cs, int columnIndex) throws SQLException {
    return parse(cs.getString(columnIndex));
  }

  private Map<String, Integer> parse(String str) {
    return Arrays.stream(str.split(",")).map(s -> s.split("="))
        .collect(Collectors.toMap(arr -> arr[0], arr -> Integer.valueOf(arr[1]), (v1, v2) -> v2, TreeMap::new));
  }
}
