package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
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
  public void shouldGetValidProductsForSequentialDates() {
    Date rangeStart = dateOf(2019, 1, 1);
    Date rangeEnd = dateOf(2019, 1, 7);
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Map<Date, List<Product>> validProducts = mapper.getProductsForSequentialDates(rangeStart, rangeEnd);
      assertEquals(7, validProducts.size());
      assertEquals(1, validProducts.get(rangeStart).size());
      assertEquals(2, validProducts.get(dateOf(2019, 1, 2)).size());
    }
  }

  @Test
  public void shouldGetValidProductsForRandomDates() {
    List<Date> dates = Arrays.asList(dateOf(2019, 1, 1), dateOf(2019, 1, 3), dateOf(2019, 1, 5));
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Map<Date, List<Product>> validProducts = mapper.getProductsForRandomDates(dates);
      assertEquals(3, validProducts.size());
      assertEquals(1, validProducts.get(dateOf(2019, 1, 1)).size());
      assertEquals(2, validProducts.get(dateOf(2019, 1, 3)).size());
      assertEquals(0, validProducts.get(dateOf(2019, 1, 5)).size());
    }
  }

  private Date dateOf(int year, int month, int day) {
    return Date.from(LocalDate.of(year, month, day).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }
}
