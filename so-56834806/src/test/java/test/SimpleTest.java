package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
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
    // prepare objects in the database
    try (SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        Reader reader = Resources.getResourceAsReader("test/CreateDB.sql")) {
      ScriptRunner runner = new ScriptRunner(conn);
      runner.setLogWriter(null);
      runner.runScript(reader);
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void demoStoredProcedure() {
    // INSERT an array of user-defined object via procedure.
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      List<User> users = new ArrayList<>();
      users.add(new User(1, "User1"));
      users.add(new User(2, "User2"));
      Map<String, ?> outParam = new HashMap<>();
      mapper.saveUsers(new User(3, "User3"), users, "My", outParam);
      // Assert OUT params
      assertEquals(Integer.valueOf(3), outParam.get("userCount"));
      assertEquals("MyUser1", ((User)outParam.get("userStruct")).getName());
      List<User> userStructArray = (List<User>)outParam.get("userStructArray");
      assertEquals(3, userStructArray.size());
      assertEquals("MyUser2", userStructArray.get(1).getName());
      List<User> userCursor= (List<User>)outParam.get("userCursor");
      assertEquals(3, userCursor.size());
      assertEquals("MyUser3", userCursor.get(2).getName());
      sqlSession.commit();
    }
    // SELECT to verify the inserted rows.
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      List<User> users = mapper.selectUsers();
      assertEquals(3, users.size());
      assertEquals(Integer.valueOf(1), users.get(0).getId());
      assertEquals("MyUser1", users.get(0).getName());
      assertEquals(Integer.valueOf(2), users.get(1).getId());
      assertEquals("MyUser2", users.get(1).getName());
      assertEquals(Integer.valueOf(3), users.get(2).getId());
      assertEquals("MyUser3", users.get(2).getName());
    }
  }
}
