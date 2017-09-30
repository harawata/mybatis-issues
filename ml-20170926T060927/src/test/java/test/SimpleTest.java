/**
 *    Copyright 2009-2017 the original author or authors.
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
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import oracle.jdbc.driver.OracleConnection;

public class SimpleTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void setUp() throws Exception {
    Reader reader = Resources.getResourceAsReader("test/mybatis-config.xml");
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    reader.close();

    // prepare objects in Oracle database
    SqlSession session = sqlSessionFactory.openSession();
    Connection conn = session.getConnection();
    reader = Resources.getResourceAsReader("test/CreateDB.sql");
    ScriptRunner runner = new ScriptRunner(conn);
    runner.setLogWriter(null);
    runner.runScript(reader);
    reader.close();
    session.close();
  }

  @Test
  public void shouldInsert() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      List<Bkdataset> list = new ArrayList<Bkdataset>();
      Bkdataset bkdataset1 = new Bkdataset("word 1", "word 2", "word 3");
      list.add(bkdataset1);
      Bkdataset bkdataset2 = new Bkdataset("word 1", "word 2", "word 3");
      list.add(bkdataset2);
      mapper.saveSyncBankInfos(list);
    } finally {
      sqlSession.close();
    }
  }

  @Test
  public void shouldInsertWithJdbc() throws Exception {
    SqlSession session = sqlSessionFactory.openSession();
    Connection conn = session.getConnection();

    List<Struct> bankInfoList = new ArrayList<Struct>();
    Struct bankInfo1 = conn.createStruct("S_BANK_INFO", new Object[] { "aa", "bb", "cc" });
    Struct bankInfo2 = conn.createStruct("S_BANK_INFO", new Object[] { "dd", "ee", "ff" });
    bankInfoList.add(bankInfo1);
    bankInfoList.add(bankInfo2);

    Array array = ((OracleConnection) conn).createOracleArray("S_BANK_INFO_LST", bankInfoList.toArray());

    CallableStatement ps = conn.prepareCall("{call proc_bank_info_sync(?)}");
    ps.setArray(1, array);
    assertFalse(ps.execute());
    conn.commit();
  }
}
