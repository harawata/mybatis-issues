package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
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
  public void shouldReturnOnePerson() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Map<String, Object> parameter = new HashMap<String, Object>();
      parameter.put("personId", 1);
      mapper.getOutCursor(parameter);
      @SuppressWarnings("unchecked")
      List<Person> persons = (List<Person>) parameter.get("persons");
      assertEquals(1, persons.size());
      assertEquals(Integer.valueOf(1), persons.get(0).getId());
    } finally {
      sqlSession.close();
    }
  }

  @Test
  public void shouldReturnEmptyListIfNoResult() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Map<String, Object> parameter = new HashMap<String, Object>();
      parameter.put("personId", 3);
      mapper.getOutCursor(parameter);
      @SuppressWarnings("unchecked")
      List<Person> persons = (List<Person>) parameter.get("persons");
      assertTrue(persons.isEmpty());
    } finally {
      sqlSession.close();
    }
  }
}
