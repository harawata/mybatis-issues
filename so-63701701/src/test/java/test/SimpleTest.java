package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
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

  @Test
  public void testLayer1() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Map<String, Object> param = new HashMap<>();
      param.put("layer_name", "layer1");
      param.put("field_name", "id");
      param.put("object_id", "2");
      Map<String, Object> result = mapper.test(param);
      assertEquals(2, result.size());
      assertEquals(2, result.get("id"));
      assertEquals("B", result.get("name"));
    }
  }
  @Test
  public void testLayer2() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Map<String, Object> param = new HashMap<>();
      param.put("layer_name", "layer2");
      param.put("field_name", "id");
      param.put("object_id", "20");
      Map<String, Object> result = mapper.test(param);
      assertEquals(3, result.size());
      assertEquals(20, result.get("id"));
      assertEquals(4, result.get("count_"));
      assertEquals("y", result.get("name"));
    }
  }

}
