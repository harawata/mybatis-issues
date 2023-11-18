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
  public void test() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      {
        Map<String, Long> param = new HashMap<>();
        param.put("incrementCountAlpha", null);
        param.put("incrementCountBeta", Long.valueOf(100));
        assertEquals(1, mapper.updateColumn1(param));
        String json = mapper.selectColumn1(1);
        assertTrue(json.contains("\"key_alpha\": 0"));
        assertTrue(json.contains("\"key_beta\": 100"));
      }
      {
        Map<String, Long> param = new HashMap<>();
        param.put("incrementCountAlpha", Long.valueOf(1));
        param.put("incrementCountBeta", Long.valueOf(100));
        assertEquals(1, mapper.updateColumn1(param));
        String json = mapper.selectColumn1(1);
        assertTrue(json.contains("\"key_alpha\": 1"));
        assertTrue(json.contains("\"key_beta\": 200"));
      }
      {
        Map<String, Long> param = new HashMap<>();
        param.put("incrementCountAlpha", Long.valueOf(2));
        param.put("incrementCountBeta", null);
        assertEquals(1, mapper.updateColumn1(param));
        String json = mapper.selectColumn1(1);
        assertTrue(json.contains("\"key_alpha\": 3"));
        assertTrue(json.contains("\"key_beta\": 200"));
      }
    }
  }

}
