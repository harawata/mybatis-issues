/**
 *    Copyright 2019 the original author or authors.
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

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeClass
  public static void setUp() throws Exception {
    try (Reader reader = Resources.getResourceAsReader("test/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }
  }

  @Test
  public void shouldGetAUser() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);
      UserWiseData userWiseData = mapper.fetchUserWiseData();
      assertEquals("3520", userWiseData.getPersonId());
      TypeWiseData mandatorySTC = userWiseData.getMandatorySTC();
      assertEquals(2, mandatorySTC.getHrCompanyWiseData().size());
      HRCompanyWiseData hrCompanyWiseData1 = mandatorySTC.getHrCompanyWiseData().get(0);
      assertEquals("EFGH", hrCompanyWiseData1.getCompanyCode());
      assertEquals(3, hrCompanyWiseData1.getNonMidYear().getInvestmentDateWiseData().get(0).getVestingDateWiseData().size());
      HRCompanyWiseData hrCompanyWiseData2 = mandatorySTC.getHrCompanyWiseData().get(1);
      assertEquals("ABCD", hrCompanyWiseData2.getCompanyCode());
      assertEquals(2, hrCompanyWiseData2.getNonMidYear().getInvestmentDateWiseData().get(0).getVestingDateWiseData().size());
    }
  }
}
