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
  public void shouldGetABlog() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      Blog blog = mapper.getBlog(1);
      Author author = blog.getAuthor();
      List<Post> posts = blog.getPosts();
      Post post1 = posts.get(0);
      assertSame(blog, post1.getBlog());
      assertNotSame(author, post1.getAuthor());
      Post post2 = posts.get(1);
      assertSame(blog, post2.getBlog());
      Post post3 = posts.get(2);
      assertSame(blog, post3.getBlog());
    }
  }

  @Test
  public void shouldGetABlogWithResultHandler() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      BlogResultHandler resultHandler = new BlogResultHandler();
      mapper.getBlogs(resultHandler);

      List<Blog> blogs = resultHandler.getBlogs();
      assertEquals(3, blogs.size());

      Blog blog1 = blogs.get(0);
      Blog blog2 = blogs.get(1);
      Blog blog3 = blogs.get(2);

      Author mia = blog1.getAuthor();
      Author bob = blog2.getAuthor();

      assertSame(mia, blog3.getAuthor());

      List<Post> blog1Posts = blog1.getPosts();
      assertSame(mia, blog1Posts.get(0).getAuthor());
      assertSame(bob, blog1Posts.get(1).getAuthor());
      assertSame(mia, blog1Posts.get(2).getAuthor());

      List<Post> blog2Posts = blog2.getPosts();
      assertSame(mia, blog2Posts.get(0).getAuthor());
      assertSame(mia, blog2Posts.get(1).getAuthor());

      List<Post> blog3Posts = blog3.getPosts();
      assertSame(bob, blog3Posts.get(0).getAuthor());
      assertSame(mia, blog3Posts.get(1).getAuthor());
    }
  }
}
