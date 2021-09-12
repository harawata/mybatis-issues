package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
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
    // prepare in-memory database
    try (SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        Reader reader = Resources.getResourceAsReader("test/CreateDB.sql")) {
      ScriptRunner runner = new ScriptRunner(conn);
      runner.setLogWriter(null);
      runner.runScript(reader);
    }
  }

  @SuppressWarnings("unchecked")
  @Test
  public void shouldGetMultipleResultSets() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Map<String, String> map = new HashMap<>();
      map.put("p1", "yummy");
      map.put("p2", "cute");
      List<List<?>> results = mapper.execProc(map);
      assertEquals(2, results.size());
      List<Fruit> fruits = (List<Fruit>) results.get(0);
      Fruit fruit1 = fruits.get(0);
      assertEquals(Integer.valueOf(1), fruit1.getId());
      assertEquals("Apple", fruit1.getName());
      assertEquals(Integer.valueOf(2), fruit1.getQty());
      Fruit fruit2 = fruits.get(1);
      assertEquals(Integer.valueOf(2), fruit2.getId());
      assertEquals("Orange", fruit2.getName());
      assertEquals(Integer.valueOf(3), fruit2.getQty());
      Fruit fruit3 = fruits.get(2);
      assertEquals(Integer.valueOf(3), fruit3.getId());
      assertEquals("Banana", fruit3.getName());
      assertEquals(Integer.valueOf(5), fruit3.getQty());
      List<Animal> animals = (List<Animal>) results.get(1);
      Animal animal1 = animals.get(0);
      assertEquals("monkey", animal1.getType());
      assertEquals(Integer.valueOf(2000), animal1.getNum());
      assertEquals("London", animal1.getLoc());
      Animal animal2 = animals.get(1);
      assertEquals("dog", animal2.getType());
      assertEquals(Integer.valueOf(3000), animal2.getNum());
      assertEquals("New York", animal2.getLoc());
      Animal animal3 = animals.get(2);
      assertEquals("cat", animal3.getType());
      assertEquals(Integer.valueOf(8000), animal3.getNum());
      assertEquals("LA", animal3.getLoc());
    }
  }
}
