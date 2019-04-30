package net.harawata.so55865038;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.migration.options.DatabaseOperationOption;
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
    String CHANGELOG_TABLE = "databaseChangelog";
    String scriptsDir = "scripts";
    Properties properties = new Properties();
    properties.setProperty("changelog", CHANGELOG_TABLE);
    DatabaseOperationOption options = new DatabaseOperationOption();
    options.setChangelogTable(CHANGELOG_TABLE);
    MyBatisMigrationsPopulator populator = new MyBatisMigrationsPopulator(dataSource, scriptsDir, properties, options);
    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(populator);
    return dataSourceInitializer;
  }

}
