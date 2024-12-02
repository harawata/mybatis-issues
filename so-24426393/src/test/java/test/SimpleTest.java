package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class SimpleTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeAll
  public static void setUp() throws Exception {
    // create an SqlSessionFactory
    try (Reader reader = Resources.getResourceAsReader("test/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }
    // prepare in-memory database
    try (SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        Reader reader = Resources.getResourceAsReader("test/CreateDB.sql")) {
      ScriptRunner runner = new ScriptRunner(conn);
      runner.setLogWriter(null);
      runner.runScript(reader);
    }
  }

  @ParameterizedTest
  @CsvSource({ "true,true", "true,false", "false,true", "false,false" })
  void testInOutStruct(boolean legacy, boolean select) throws Exception {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      // prepare parameters
      Map<String, Object> params = new HashMap<>();
      Connection connection = sqlSession.getConnection();
      if (legacy) {
        params.put("io_calc", createStructLegacy(connection));
      } else {
        params.put("io_calc", createStruct(connection));
      }
      params.put("in_restart", Boolean.TRUE);
      params.put("in_user", "John");
      // call the procedure
      if (select) {
        // As the PROCEDURE does not return result,
        // it's better to use <update> or <insert>
        // which triggers transaction commit
        mapper.pr_start_select(params);
      } else {
        mapper.pr_start(params);
      }
      // verify OUT param
      Struct out = (Struct) params.get("io_calc");
      assertEquals("A", out.getAttributes()[0]);
      assertEquals("s", out.getAttributes()[1]);
      assertEquals("foo", out.getAttributes()[2]);
    }
  }

  @Test
  void testInOutStructPlainJdbc() throws SQLException {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Connection connection = sqlSession.getConnection();
      try (CallableStatement stmt = connection.prepareCall("{call pkg_24426393.pr_start(?,?,?)}")) {
        Struct struct = createStruct(connection);
        stmt.setObject(1, struct);
        stmt.setBoolean(2, false);
        stmt.setString(3, "test");
        stmt.registerOutParameter(1, Types.STRUCT, "TYPE_CALC");

        assertFalse(stmt.execute());

        // verify out param
        Struct out = stmt.getObject(1, Struct.class);
        Object[] outAttrs = out.getAttributes();
        assertEquals("A", outAttrs[0]);
        assertEquals("s", outAttrs[1]);
        assertEquals("foo", outAttrs[2]);

        // check if the original struct is modified (it's not)
        Object[] attributes = struct.getAttributes();
        assertEquals("0xD", attributes[2]);
      }
    }
  }

  private Struct createStruct(Connection connection) throws SQLException {
    return connection.createStruct("TYPE_CALC", new Object[] { "A", "s", "0xD" });
  }

  private STRUCT createStructLegacy(Connection connection) throws SQLException {
    // Not recommended because STRUCT is deprecated
    StructDescriptor descriptor = StructDescriptor.createDescriptor("TYPE_CALC", connection);
    return new STRUCT(descriptor, connection, new Object[] { "A", "s", "0xD" });
  }
}
