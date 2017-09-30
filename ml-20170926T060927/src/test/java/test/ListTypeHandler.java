package test;

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
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import oracle.jdbc.driver.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

@MappedJdbcTypes(JdbcType.ARRAY)
public class ListTypeHandler extends BaseTypeHandler<List<Bkdataset>> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, List<Bkdataset> parameter, JdbcType jdbcType)
      throws SQLException {
    System.out.println("asdfasdfasdfdasf++++++++++++++=========");
    Connection conn = ps.getConnection();
    StructDescriptor sdesc = StructDescriptor.createDescriptor("S_BANK_INFO", conn);
    List<STRUCT> structs = new ArrayList<STRUCT>();
    for (int idx = 0; idx < parameter.size(); idx++) {
      Bkdataset ds = parameter.get(idx); // Bkdataset is just PO with many string attribute
      Object[] result = { ds.getAcpicd(), ds.getAlttype(), ds.getAltdate() };
      structs.add(new STRUCT(sdesc, conn, result));
    }
    ArrayDescriptor desc = ArrayDescriptor.createDescriptor("S_BANK_INFO_LST", conn);
    ARRAY array = new ARRAY(desc, conn, structs.toArray());
    ps.setArray(i, array);
  }

  public void _setNonNullParameter(PreparedStatement ps, int i, List<Bkdataset> parameter, JdbcType jdbcType)
      throws SQLException {
    // This version does not use deprecated classes
    Connection conn = ps.getConnection();

    List<Struct> structs = new ArrayList<Struct>();
    for (int idx = 0; idx < parameter.size(); idx++) {
      Bkdataset ds = parameter.get(idx);
      Object[] result = { ds.getAcpicd(), ds.getAlttype(), ds.getAltdate() };
      structs.add(conn.createStruct("S_BANK_INFO", result));
    }
    Array array = ((OracleConnection) conn).createOracleArray("S_BANK_INFO_LST", structs.toArray());
    ps.setArray(i, array);
  }

  @Override
  public List<Bkdataset> getNullableResult(ResultSet rs, String columnName) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Bkdataset> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Bkdataset> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

}
