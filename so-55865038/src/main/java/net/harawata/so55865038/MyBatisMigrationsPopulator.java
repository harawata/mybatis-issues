package net.harawata.so55865038;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.migration.DataSourceConnectionProvider;
import org.apache.ibatis.migration.FileMigrationLoader;
import org.apache.ibatis.migration.MigrationException;
import org.apache.ibatis.migration.operations.UpOperation;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;

public class MyBatisMigrationsPopulator implements DatabasePopulator {

  private final DataSource dataSource;

  private final String scriptsDir;

  private final Properties properties;

  public MyBatisMigrationsPopulator(DataSource dataSource, String scriptsDir, Properties properties) {
    super();
    this.dataSource = dataSource;
    this.scriptsDir = scriptsDir;
    this.properties = properties;
  }

  @Override
  public void populate(Connection connection) throws SQLException, ScriptException {
    try {
      new UpOperation().operate(new DataSourceConnectionProvider(dataSource), createMigrationsLoader(), null, null);
    } catch (MigrationException e) {
      throw new UncategorizedScriptException("Migration failed.", e.getCause());
    }
  }

  protected FileMigrationLoader createMigrationsLoader() {
    URL url = getClass().getClassLoader().getResource(scriptsDir);
    File dir = new File(url.getFile());
    return new FileMigrationLoader(dir, "utf-8", properties);
  }

}
