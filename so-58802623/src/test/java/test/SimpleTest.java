package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

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
  public void callMyProc() throws Exception {
    Locale.setDefault(Locale.ENGLISH);
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    Date date;
    date = sdf.parse("01-Oct-1984");

    StoredProcInput inParam = new StoredProcInput();
    inParam.setSP_Age(28);
    inParam.setSP_Birthday(date);
    inParam.setSP_Gender("M");
    inParam.setSP_FirstName("Joe");
    inParam.setSP_LastName("Higashi");

    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      List<StoredProcOutput> results = mapper.callStoredProcedure(inParam);
      assertEquals(1, results.size());
      assertEquals("Jolly good fellow", results.get(0).getOutput());
    }
  }

}
