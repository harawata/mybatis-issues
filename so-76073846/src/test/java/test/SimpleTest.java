package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
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
  public void shouldInsertAUser() {
    GenericRecordMap param = GenericRecordMap.builder().tableName("users").pk("userid")
        .tableMap(Map.of("name", "User1", "age", 98)).build();

    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      mapper.insert(param);
      sqlSession.commit();
    }

    // Verify inserted data
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      User user = mapper.selectById(param.getId());
      assertEquals("User1", user.getName());
      assertEquals(Integer.valueOf(98), user.getAge());
    }
  }

}
