package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.util.Date;

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
    final IdentifierResult result = new IdentifierResult();
    final Date time = new Date();
    final Integer duration = 180;
    final Integer summaryId = 99;

    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Event event = new Event();
      event.setTime(time);
      event.setDuration(180);
      mapper.insert(event, summaryId, result );
      // Verify generated key is assigned to 'result'
      assertNotNull(result.getId());
      sqlSession.commit();
    }

    // Verify the ID is set in DB
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Event event = mapper.get(result.getId());
      assertEquals(time, event.getTime());
      assertEquals(duration, event.getDuration());
    }
  }

}
