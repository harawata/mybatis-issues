package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.util.List;

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
  public void getOneDivideOut() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      DivideOut out = mapper.selectMultiple();
      List<Movie> movies = out.movie;
      assertEquals(3, movies.size());
      assertEquals("Movie1", movies.get(0).getName());
      assertEquals("Movie2", movies.get(1).getName());
      assertEquals("Movie3", movies.get(2).getName());
      List<Artist> artists = out.artist;
      assertEquals(2, artists.size());
      assertEquals("John", artists.get(0).getName());
      assertEquals("Jane", artists.get(1).getName());
    }
  }

  @Test
  public void getTwoLists() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      List<List<?>> lists = mapper.selectTwoLists();
      @SuppressWarnings("unchecked")
      List<Movie> movies = (List<Movie>) lists.get(0);
      assertEquals(3, movies.size());
      assertEquals("Movie1", movies.get(0).getName());
      assertEquals("Movie2", movies.get(1).getName());
      assertEquals("Movie3", movies.get(2).getName());
      @SuppressWarnings("unchecked")
      List<Artist> artists = (List<Artist>) lists.get(1);
      assertEquals(2, artists.size());
      assertEquals("John", artists.get(0).getName());
      assertEquals("Jane", artists.get(1).getName());
    }
  }

}
