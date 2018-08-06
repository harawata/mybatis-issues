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

import java.io.Reader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collections;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.distribution.Version;
import ru.yandex.qatools.embed.postgresql.util.SocketUtil;

public class SimpleTest {

  private static final EmbeddedPostgres postgres = new EmbeddedPostgres(Version.Main.V10);

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void setUp() throws Exception {
    // Launch PostgreSQL server. Download / unarchive if necessary.
    // To use your own installation, comment out the following line and build the URL manually.
    final String url = postgres.start(
        EmbeddedPostgres.cachedRuntimeConfig(Paths.get(System.getProperty("java.io.tmpdir"), "pgembed")), "localhost",
        SocketUtil.findFreePort(), "gh1325", "postgres", "root", Collections.<String> emptyList());

    final String username = null;
    final String password = null;

    Configuration configuration = new Configuration();
    Environment environment = new Environment("development", new JdbcTransactionFactory(), new UnpooledDataSource(
        "org.postgresql.Driver", url, username, password));
    configuration.setEnvironment(environment);
    configuration.addMapper(Mapper.class);
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

    // Prepare tables and rows.
    SqlSession session = sqlSessionFactory.openSession();
    Connection conn = session.getConnection();
    Reader reader = Resources.getResourceAsReader("test/CreateDB.sql");
    ScriptRunner runner = new ScriptRunner(conn);
    runner.setLogWriter(null);
    runner.runScript(reader);
    reader.close();
    session.close();
  }

  @AfterClass
  public static void tearDown() {
    postgres.stop();
  }

  @Test
  public void shouldReturnStringFromTextColumn() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      User user = mapper.getUser(1);
      Assert.assertEquals("User1", user.getName());
      Assert.assertEquals("description 1", user.getDescription());
    } finally {
      sqlSession.close();
    }
  }

  @Test
  public void shouldReportVarcharForTextColumn() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      // Plain old JDBC code
      Connection conn = sqlSession.getConnection();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select description from users where id = 1");
      ResultSetMetaData rsmd = rs.getMetaData();
      Assert.assertEquals(Types.VARCHAR, rsmd.getColumnType(1));
    } finally {
      sqlSession.close();
    }
  }

  @Test
  public void shouldInsertStringIntoTextColumn() {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      User user = new User();
      user.setId(2);
      user.setName("User2");
      user.setDescription("description 2");
      mapper.insertUser(user);
      sqlSession.commit();
    } finally {
      sqlSession.close();
    }
    // Verify the inserted data.
    sqlSession = sqlSessionFactory.openSession();
    try {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      User user = mapper.getUser(2);
      Assert.assertEquals("User2", user.getName());
      Assert.assertEquals("description 2", user.getDescription());
    } finally {
      sqlSession.close();
    }
  }

}
