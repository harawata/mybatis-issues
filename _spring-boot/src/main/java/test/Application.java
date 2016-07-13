
package test;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@MapperScan("mapper")
public class Application {

  @Bean
  SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    factoryBean.setDataSource(dataSource);
    return factoryBean;
  }

  @Bean
  EmbeddedDatabaseFactoryBean dataSource() {
    EmbeddedDatabaseFactoryBean factoryBean = new EmbeddedDatabaseFactoryBean();
    factoryBean.setDatabaseType(EmbeddedDatabaseType.HSQL);
    factoryBean.setDatabaseName("mybatisissues");
    factoryBean.setDatabasePopulator(
        new ResourceDatabasePopulator(new ClassPathResource("test/CreateDB.sql")));
    return factoryBean;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
