package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
  public void testSo61822181() throws Exception {
    ExecutorService executorService = Executors.newCachedThreadPool();
    try {
      for (int iteration = 0; iteration < 100; iteration++) {
        List<Future<User>> results = IntStream.range(0, 2).mapToObj(i -> executorService.submit(this::selectUser))
            .collect(Collectors.toList());
        for (Future<User> result : results) {
          assertEquals("User1", result.get().getName());
        }
      }
    } finally {
      executorService.shutdown();
    }
  }

  private User selectUser() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      return mapper.getUser(1);
    }
  }

  @Test
  public void testSo61822181_WhatIfConnectionIsNotClosed() throws Exception {
    ExecutorService executorService = Executors.newCachedThreadPool();
    try {
      for (int iteration = 0; iteration < 5; iteration++) {
        List<Future<User>> results = IntStream.range(0, 2)
            .mapToObj(i -> executorService.submit(this::selectUserWithoutClosingConnection))
            .collect(Collectors.toList());
        for (Future<User> result : results) {
          assertEquals("User1", result.get().getName());
        }
      }
    } finally {
      executorService.shutdown();
    }
  }

  private User selectUserWithoutClosingConnection() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    Mapper mapper = sqlSession.getMapper(Mapper.class);
    return mapper.getUser(1);
    // connection is not closed
  }
}
