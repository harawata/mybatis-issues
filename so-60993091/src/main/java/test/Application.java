package test;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@MapperScan("mapper")
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
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
