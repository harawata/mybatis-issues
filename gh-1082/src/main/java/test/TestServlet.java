package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import test.domain.User;

@WebServlet("/test")
public class TestServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static SqlSessionFactory sqlSessionFactory;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    // populate in-memory database
    try {
      runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
          "CreateDB.sql");
    } catch (SQLException e) {
      throw new ServletException(e);
    }

    User user = null;
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      user = sqlSession.selectOne("test.Mapper.getUser", 1);

    }

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<html><head></head><body>");
    out.println("<p>Name: " + user.getName() + "</p>");
    out.println("</body></html>");
  }

  public static void runScript(DataSource ds, String resource) throws IOException, SQLException {
    try (Connection connection = ds.getConnection()) {
      ScriptRunner runner = new ScriptRunner(connection);
      runner.setAutoCommit(true);
      runner.setStopOnError(false);
      runner.setLogWriter(null);
      runner.setErrorLogWriter(null);
      runScript(runner, resource);
    }
  }

  public static void runScript(ScriptRunner runner, String resource) throws IOException, SQLException {
    try (Reader reader = Resources.getResourceAsReader(resource)) {
      runner.runScript(reader);
    }
  }
}
