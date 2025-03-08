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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void setUp() throws Exception {
    // Enable trace version < 23 (requires debug driver)
    // https://mvnrepository.com/artifact/com.oracle.database.jdbc.debug
    // System.setProperty("oracle.jdbc.Trace", "true");

    // Enable trace version >= 23
    System.setProperty("oracle.jdbc.diagnostic.enableLogging", "true");

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

  @Before
  public void beforeEach() throws Exception {
    // Re-init table
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      mapper.deleteAll();
      sqlSession.commit();
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
      System.out.println("Calling saveUsers");
      mapper.saveUsers(new User(3, "User3"), users, "My", outParam);
      System.out.println("Called saveUsers");

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

    selectToVerify();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void demoStoredProcedure_SQLData_So79490964() throws Exception {
    // A demo for so-79490964. Calling the same PROCEDURE using SQLData instead of Struct.
    // https://stackoverflow.com/q/79490964/1261766
    // This answer says `createStruct()` is expensive and `SQLData` is more efficient.
    // https://stackoverflow.com/a/25991992/1261766
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      // To extract OUT param correctly, the type map must be updated.
      Connection con = sqlSession.getConnection();
      Map<String, Class<?>> typeMap = con.getTypeMap();
      typeMap.put("S_USER_OBJ", SqlDataUser.class);
      con.setTypeMap(typeMap);

      Mapper mapper = sqlSession.getMapper(Mapper.class);

      List<SqlDataUser> users = List.of(new SqlDataUser(1, "User1"), new SqlDataUser(2, "User2"));

      Map<String, ?> outParam = new HashMap<>();
      System.out.println("Calling sqlDataSaveUsers");
      mapper.sqlDataSaveUsers(new SqlDataUser(3, "User3"), users, "My", outParam);
      System.out.println("Called sqlDataSaveUsers");

      // Assert OUT params
      assertEquals(Integer.valueOf(3), outParam.get("userCount"));
      assertEquals("MyUser1", ((SqlDataUser)outParam.get("userStruct")).getName());

      List<SqlDataUser> userStructArray = (List<SqlDataUser>)outParam.get("userStructArray");
      assertEquals(3, userStructArray.size());
      assertEquals("MyUser2", userStructArray.get(1).getName());

      List<User> userCursor= (List<User>)outParam.get("userCursor");
      assertEquals(3, userCursor.size());
      assertEquals("MyUser3", userCursor.get(2).getName());

      sqlSession.commit();
    }

    selectToVerify();
  }

  private void selectToVerify() {
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
