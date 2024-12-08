package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
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
  public void demoWithResultHandler() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);

      List<String> years = List.of("2022", "2023");
      List<String> months = List.of("01", "02");

      // Prepare parameter
      QueryRequest param = new QueryRequest();
      param.setYears(years);
      param.setMonths(months);

      // Create a result handler.
      TaskCountResponseResultHandler resultHandler = new TaskCountResponseResultHandler(years, months);
      // Execute query.
      mapper.selectWithResultHandler(param, resultHandler);
      // Get the list from the result handler.
      assertResult(resultHandler.getResult());
    }
  }

  @Test
  public void demoWithTypeHandler() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);

      List<String> years = List.of("2022", "2023");
      List<String> months = List.of("01", "02");

      // Prepare parameter
      QueryRequest param = new QueryRequest();
      param.setYears(years);
      param.setMonths(months);

      // Execute query.
      assertResult(mapper.selectWithTypeHandler(param));
    }
  }

  private void assertResult(List<TaskCountResponse> result) {
    assertEquals(3, result.size());

    {
      TaskCountResponse r = result.get(0);
      assertEquals("A", r.getRegion());
      assertEquals("A1", r.getOffice());
      assertEquals(Map.of(
          "month_2022_01", 2,
          "month_2022_02", 1,
          "month_2023_01", 0,
          "month_2023_02", 0), r.getMonthlyCounts());
    }
    {
      TaskCountResponse r = result.get(1);
      assertEquals("A", r.getRegion());
      assertEquals("A2", r.getOffice());
      assertEquals(Map.of(
          "month_2022_01", 0,
          "month_2022_02", 1,
          "month_2023_01", 0,
          "month_2023_02", 0), r.getMonthlyCounts());
    }
    {
      TaskCountResponse r = result.get(2);
      assertEquals("B", r.getRegion());
      assertEquals("B1", r.getOffice());
      assertEquals(Map.of(
          "month_2022_01", 0,
          "month_2022_02", 0,
          "month_2023_01", 1,
          "month_2023_02", 2), r.getMonthlyCounts());
    }
  }
}
