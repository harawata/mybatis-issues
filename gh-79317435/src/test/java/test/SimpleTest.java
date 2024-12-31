/**
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import static org.junit.Assert.assertNotNull;

class SimpleTest
{

	private static SqlSessionFactory sqlSessionFactory;

	@BeforeAll
	static void setUp() throws Exception
	{
		String url = "jdbc:tc:mysql:5.6.43://hostname/?TC_INITSCRIPT=test/CreateDB.sql&TC_MY_CNF=mysql_conf&TC_DAEMON=true";
		Configuration configuration = new Configuration();
		Environment environment = new Environment("development", new JdbcTransactionFactory(), new UnpooledDataSource("com.mysql.cj.jdbc.Driver", url, "root", ""));
		configuration.setEnvironment(environment);
		configuration.addMapper(Mapper.class);
		configuration.setLogImpl(Slf4jImpl.class);
		configuration.setObjectWrapperFactory(new MyObjectWrapperFactory());
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
	}

	@Test
	void test()
	{
		try ( SqlSession sqlSession = sqlSessionFactory.openSession() ) {
			Map<String, Object> param = ImmutableMap.of("tableName", "test_1");
			// This should return a row of data, but an error is now reported
			List result = sqlSession.selectList("queryTableData", param);
			assertNotNull(result);
		}
	}
}
