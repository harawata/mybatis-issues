package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.executor.BatchResult;
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
    try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)){
      List<BatchResult> results = new ArrayList<>();
      final int batchSize = 20000;
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      final int size = 100000;
      for (int i = 0; i < size;) {
        mapper.insertUser(user(i));
        i++;
        if (i % batchSize == 0 || i == size) {
          results.addAll(sqlSession.flushStatements());
          sqlSession.clearCache();
        }
      }
      sqlSession.commit();
    }
    try (SqlSession sqlSession = sqlSessionFactory.openSession()){
      List<BatchResult> results = new ArrayList<>();
      final int batchSize = 20000;
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      assertEquals(100000, mapper.countUsers());
    }
  }

  private User user(int i) {
    return new User(++i, "name" + ++i, "name" + ++i, "name" + ++i, "name" + ++i, "name" + ++i, "name" + ++i, "name" + ++i,
        "name" + ++i, "name" + ++i, "name" + ++i, "name" + ++i, "name" + ++i, "name" + ++i, "name" + ++i);
  }
}
