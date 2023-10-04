package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;

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
  public void testMapperBCache() {
    Pet pet;
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      MapperB mapper = sqlSession.getMapper(MapperB.class);
      pet = mapper.getPet(1);
      assertEquals("Pet1", pet.getName());
    }
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      MapperA mapper = sqlSession.getMapper(MapperA.class);
      pet.setName("Carl");
      assertEquals(1, mapper.updatePet(pet));
      sqlSession.commit();
    }
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      MapperB mapper = sqlSession.getMapper(MapperB.class);
      pet = mapper.getPet(1);
      assertEquals("Pet1", pet.getName());
    }
  }

  @Test
  public void testMapperBCache2() {
    Pet pet;
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      MapperB mapper = sqlSession.getMapper(MapperB.class);
      pet = mapper.getPet(1);
      assertEquals("Pet1", pet.getName());
    }
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      MapperA mapper = sqlSession.getMapper(MapperA.class);
      User user = mapper.getUser(1);
      assertEquals("Pet1", user.getPet().getName());
      pet.setName("Carl");
      assertEquals(1, mapper.updatePet(pet));
      sqlSession.commit();
    }
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      MapperB mapper = sqlSession.getMapper(MapperB.class);
      pet = mapper.getPet(1);
      assertEquals("Pet1", pet.getName());
    }
  }

}
