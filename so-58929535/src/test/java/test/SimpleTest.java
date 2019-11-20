package test;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class SimpleTest {
  private static final Logger log = LogManager.getLogger(SimpleTest.class);

  @Test
  public void shouldRollbackOnError() throws Exception {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:tc:mysql:5.7.20://hostname/?TC_DAEMON=true";
    String username = "root";
    String password = "";

    Class.forName(driver);
    try (Connection conn = DriverManager.getConnection(url, username, password);
        Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("script.sql"),
            StandardCharsets.UTF_8)) {
      conn.setAutoCommit(false);
      ScriptRunner runner = new ScriptRunner(conn);
      runner.setAutoCommit(false);
      runner.setStopOnError(true);
      runner.setSendFullScript(false);
      runner.setDelimiter(";");
      runner.setFullLineDelimiter(false);
      runner.setLogWriter(null);
      try {
        runner.runScript(reader);
      } catch (RuntimeSqlException e) {
        log.debug("This is expected", e);
      }
      // Verify the result
      try (Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery("select count(*) from daiql_test2")) {
        // The table exists because DDL is not rolled back, but it should be empty.
        assertTrue(rs.next());
        assertEquals(0, rs.getInt(1));
      }
    }
  }

}
