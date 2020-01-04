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
  public void shouldGetAPerson() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Person person = mapper.getPersonPet(1);
      assertEquals("Mary", person.getName());
      // verify list of pets
      assertEquals(2, person.getPets().size());
      assertEquals("Cat", person.getPets().get(0).getName());
      assertEquals("Snake", person.getPets().get(1).getName());
      // verify list of strings
      assertEquals(2, person.getPetNames().size());
      assertEquals("Cat", person.getPetNames().get(0));
      assertEquals("Snake", person.getPetNames().get(1));
    }
  }

  @Test
  public void shouldGetAllPersons() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      List<Person> persons = mapper.getAllPersonPet();
      assertEquals(2, persons.size());

      Person mary = persons.get(0);
      assertEquals("Mary", mary.getName());
      // verify list of pets
      assertEquals(2, mary.getPets().size());
      assertEquals("Cat", mary.getPets().get(0).getName());
      assertEquals("Snake", mary.getPets().get(1).getName());
      // verify list of strings
      assertEquals(2, mary.getPetNames().size());
      assertEquals("Cat", mary.getPetNames().get(0));
      assertEquals("Snake", mary.getPetNames().get(1));
      // verify list of pets
      Person joe = persons.get(1);
      assertEquals(1, joe.getPets().size());
      assertEquals("Dog", joe.getPets().get(0).getName());
      // verify list of strings
      assertEquals(1, joe.getPetNames().size());
      assertEquals("Dog", joe.getPetNames().get(0));
    }
  }

}
