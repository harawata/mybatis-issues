/**
 *    Copyright 2009-2015 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package test;

import static org.junit.Assert.*;

import java.io.Reader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Collections;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.distribution.Version;
import ru.yandex.qatools.embed.postgresql.util.SocketUtil;

public class PostgresSQLXMLDemo {

  private static final EmbeddedPostgres postgres = new EmbeddedPostgres(Version.V9_6_5);

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void setUp() throws Exception {
    // Launch PostgreSQL server. Download / unarchive if necessary.
    String url = postgres.start(
        EmbeddedPostgres.cachedRuntimeConfig(Paths.get(System.getProperty("java.io.tmpdir"), "pgembed")), "localhost",
        SocketUtil.findFreePort(), "postgres_genkeys", "postgres", "root", Collections.emptyList());

    Configuration configuration = new Configuration();
    Environment environment = new Environment("development", new JdbcTransactionFactory(), new UnpooledDataSource(
        "org.postgresql.Driver", url, null));
    configuration.setLogImpl(Log4j2Impl.class);
    configuration.setEnvironment(environment);
    configuration.setUseGeneratedKeys(true);
    configuration.addMapper(Mapper.class);
    configuration.getTypeHandlerRegistry().register(new SqlxmlTypeHandler());
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    try (SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        Reader reader = Resources
            .getResourceAsReader("test/CreateDB.sql")) {
      ScriptRunner runner = new ScriptRunner(conn);
      runner.setLogWriter(null);
      runner.runScript(reader);
    }
  }

  @AfterClass
  public static void tearDown() {
    postgres.stop();
  }

  @Test
  public void testSelect() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      User user = mapper.select(1);
      assertEquals("<oops>okay</oops>", user.getData());
    } finally {
      sqlSession.close();
    }
  }

  @Test
  public void testInsert() throws Exception {
    String data = "<oops>nah</oops>";
    {
      SqlSession sqlSession = sqlSessionFactory.openSession();
      try {
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        User user = new User();
        user.setId(2);
        user.setData(data);
        int result = mapper.insert(user);
        assertEquals(1, result);
        sqlSession.commit();
      } finally {
        sqlSession.close();
      }
    }
    {
      SqlSession sqlSession = sqlSessionFactory.openSession();
      try {
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        User user = mapper.select(2);
        assertEquals(data, user.getData());
      } finally {
        sqlSession.close();
      }
    }
  }

  @Test
  public void testInsertWithoutTypeHandler() throws Exception {
    String data = "<oops>nah</oops>";
    {
      SqlSession sqlSession = sqlSessionFactory.openSession();
      try {
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        User user = new User();
        user.setId(3);
        user.setData(data);
        int result = mapper.insertWithoutTypeHandler(user);
        assertEquals(1, result);
        sqlSession.commit();
      } finally {
        sqlSession.close();
      }
    }
    {
      SqlSession sqlSession = sqlSessionFactory.openSession();
      try {
        Mapper mapper = sqlSession.getMapper(Mapper.class);
        User user = mapper.select(3);
        assertEquals(data, user.getData());
      } finally {
        sqlSession.close();
      }
    }
  }
}
