package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.BatchUpdateException;
import java.sql.Connection;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchExecutorException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.ExecutorType;
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
  public void shouldInsertAUser() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      mapper.insertUser(new User(1, "User1"));
      mapper.insertUser(new User(2, "User2"));
      sqlSession.flushStatements();
      mapper.insertUser(new User(3, "User3"));
      mapper.insertUser(new User(4, "User4"));
      sqlSession.flushStatements();
      sqlSession.commit();
      fail("Should fail as the user 3 already exists in DB");
    } catch (PersistenceException e) {
      assertEquals(BatchExecutorException.class,  e.getCause().getClass());
      assertEquals(BatchUpdateException.class,  e.getCause().getCause().getClass());
    }
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      assertNull(mapper.getUser(1));
      assertNull(mapper.getUser(2));
      assertNull(mapper.getUser(4));
    }
  }

}
