package test.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(basePackages = "mapper2", sqlSessionFactoryRef = "sqlSessionFactory2")
public class MyBatisConfig2 {

  @Bean(name = { "dataSourceProperties2" })
  @ConfigurationProperties("spring.datasource.2")
  public DataSourceProperties dataSourceProperties2() {
    return new DataSourceProperties();
  }

  @Bean(name = { "dataSource2" })
  public DataSource dataSource2(@Qualifier("dataSourceProperties2") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }

  @Bean(name = { "txManager2" })
  public PlatformTransactionManager txManager2(@Qualifier("dataSource2") DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public DataSourceInitializer dataSourceInitializer2(@Qualifier("dataSource2") DataSource dataSource) {
    ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
    resourceDatabasePopulator.addScript(new ClassPathResource("schema2.sql"));
    resourceDatabasePopulator.addScript(new ClassPathResource("data2.sql"));
    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
    return dataSourceInitializer;
  }

  @Bean(name = { "sqlSessionFactory2" })
  public SqlSessionFactory sqlSessionFactory2(@Qualifier("dataSource2") DataSource dataSource,
      MybatisProperties mybatisProperties) throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
    org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
    if (mybatisProperties.getConfiguration() != null) {
      BeanUtils.copyProperties(mybatisProperties.getConfiguration(), config);
    }
    sqlSessionFactoryBean.setConfiguration(config);
    return sqlSessionFactoryBean.getObject();
  }
}
