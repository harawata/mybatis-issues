package test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

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
        UserService userService = context.getBean(UserService.class);
        userService.insertBatch();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Mapper mapper = sqlSession.getMapper(Mapper.class);
            assertNull(mapper.getUser(1));
            assertNull(mapper.getUser(2));
            assertNull(mapper.getUser(4));
        }
    }

    @Configuration
    @ComponentScan
    @EnableTransactionManagement
    public static class SpringConfig {

        @Bean
        public DataSource dataSource() {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://192.168.0.200:3306/test?useSSL=false&rewriteBatchedStatements=true");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
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

        @Bean
        public PlatformTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Primary
        @Bean
        public SqlSession sqlSession(SqlSessionFactory sqlSessionFactory) {
            // Use this one for non-batch operation
            return new SqlSessionTemplate(sqlSessionFactory);
        }

        @Bean("batchSqlSession")
        public SqlSession batchSqlSession(SqlSessionFactory sqlSessionFactory) {
            return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        }

    }

    @Component
    public static class UserService {

        @Autowired
        @Qualifier("batchSqlSession")
        private SqlSession batchSqlSession;

        @Transactional
        public void insertBatch() {
            try {
                Mapper mapper = batchSqlSession.getMapper(Mapper.class);
                mapper.insertUser(new User(1, "User1"));
                mapper.insertUser(new User(2, "User2"));
                mapper.insertUser(new User(3, "User3"));
                mapper.insertUser(new User(4, "User4"));
                batchSqlSession.flushStatements();
                fail("Should fail as the user 3 already exists in DB");
            } catch (Exception e) {
                assertEquals(BatchUpdateException.class, e.getCause().getClass());
            }
        }

    }

}
