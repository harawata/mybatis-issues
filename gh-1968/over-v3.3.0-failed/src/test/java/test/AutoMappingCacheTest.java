package test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;
import java.sql.Connection;
import java.util.List;

/**
 * All code is same as module 'v3.3.0-worked'
 * Except mybatis version on maven pom is '3.5.5'
 * (Actually any version above 3.3.0 will make the this test fail)
 */
public class AutoMappingCacheTest {

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
        Reader reader = Resources.getResourceAsReader("test/CreateDB.sql");
        Reader spReader = Resources.getResourceAsReader("test/StoreProcedure.sql")) {
      ScriptRunner runner = new ScriptRunner(conn);
      runner.setLogWriter(null);
      runner.runScript(reader);

      ScriptRunner spRunner = new ScriptRunner(conn);
      spRunner.setLogWriter(null);
      spRunner.setSendFullScript(true);
      spRunner.runScript(spReader);
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void shouldGetMultipleResult() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      List<List<?>> results = mapper.getMultipleResultSet();
      List<AnotherEntity> anotherEntities = (List<AnotherEntity>) results.get(0);
      List<User> userWithInner = (List<User>) results.get(1);
      List<User> userWithoutInner = (List<User>) results.get(2);

      Assert.assertEquals(2, anotherEntities.size());
      Assert.assertEquals(4, userWithInner.size());
      Assert.assertEquals(2, userWithoutInner.size());
      userWithInner.forEach(user -> {
        Assert.assertNotNull(user.getInnerEntity());
        Assert.assertNotNull(user.getInnerEntity().getComplexCalculated());
      });
      userWithoutInner.forEach(user -> {
        Assert.assertNull(user.getInnerEntity());
      });
    }
  }

}
