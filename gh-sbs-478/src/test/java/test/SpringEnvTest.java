package test;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchExecutorException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.Reader;
import java.sql.BatchUpdateException;
import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * <p>description : SpringEnvTest
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/8/1 13:08
 */
public class SpringEnvTest {

    @Test
    public void testBatch() throws Exception {
        // init spring env
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
        // prepare
        try (SqlSession session = sqlSessionFactory.openSession();
             Connection conn = session.getConnection();
             Reader reader = Resources.getResourceAsReader("test/CreateDB.sql")) {
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setLogWriter(null);
            runner.runScript(reader);
        }
        // insert all
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)) {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            mapper.insertUser(new User(1, "User1"));
            mapper.insertUser(new User(2, "User2"));
            sqlSession.flushStatements();
            mapper.insertUser(new User(3, "User3"));
            mapper.insertUser(new User(4, "User4"));
            sqlSession.flushStatements();
            sqlSession.commit();
            fail("Should fail as the user 3 already exists in DB");
        } catch (PersistenceException e) {
            assertEquals(BatchExecutorException.class, e.getCause().getClass());
            assertEquals(BatchUpdateException.class, e.getCause().getCause().getClass());
        }
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            assertNull(mapper.getUser(1));
            assertNull(mapper.getUser(2));
            assertNull(mapper.getUser(4));
        }
    }

    public static class SpringConfig {

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
            dataSource.setUrl("jdbc:hsqldb:mem:mybatisissues");
            dataSource.setUsername("sa");
            return dataSource;
        }

        @Bean
        public SqlSessionFactoryBean sqlSessionFactoryBean() {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource());
            sqlSessionFactoryBean.setConfigLocation(
                    new DefaultResourceLoader().getResource("classpath:test/mybatis-config.xml"));
            return sqlSessionFactoryBean;
        }

    }

}
