package net.harawata.so55865038;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

@SpringBootApplication
public class So55865038Application {

  public static void main(String[] args) {
    SpringApplication.run(So55865038Application.class, args);
  }

  @Bean
  public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
    String scriptsDir = "scripts";
    Properties properties = new Properties();
    properties.setProperty("changelog", "CHANGELOG");
    MyBatisMigrationsPopulator populator = new MyBatisMigrationsPopulator(dataSource, scriptsDir, properties);
    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(populator);
    return dataSourceInitializer;
  }

}
