package net.harawata.so55865038;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.ibatis.migration.Change;
import org.apache.ibatis.migration.DataSourceConnectionProvider;
import org.apache.ibatis.migration.MigrationException;
import org.apache.ibatis.migration.MigrationLoader;
import org.apache.ibatis.migration.MigrationReader;
import org.apache.ibatis.migration.operations.UpOperation;
import org.apache.ibatis.migration.options.DatabaseOperationOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;

@Configuration
public class MyBatisMigrationsConfig {
  private static final String scriptsDir = "scripts";
  private static final String changelogTable = "changelog";

  @Bean
  public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
    Properties properties = new Properties();
    properties.setProperty("changelog", changelogTable);
    DatabaseOperationOption options = new DatabaseOperationOption();
    options.setChangelogTable(changelogTable);
    MyBatisMigrationsPopulator populator = new MyBatisMigrationsPopulator(dataSource, scriptsDir, properties, options,
        new PathMatchingResourcePatternResolver());
    DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(populator);
    return dataSourceInitializer;
  }

  private static class MyBatisMigrationsPopulator implements DatabasePopulator {
    private final DataSource dataSource;
    private final String scriptsDir;
    private final Properties properties;
    private final DatabaseOperationOption options;
    private final ResourcePatternResolver resourcePatternResolver;

    public MyBatisMigrationsPopulator(DataSource dataSource, String scriptsDir,
        Properties properties, DatabaseOperationOption options, ResourcePatternResolver resourcePatternResolver) {
      super();
      this.dataSource = dataSource;
      this.scriptsDir = scriptsDir;
      this.properties = properties;
      this.options = options;
      this.resourcePatternResolver = resourcePatternResolver;
    }

    public void populate(Connection connection) throws SQLException, ScriptException {
      try {
        new UpOperation().operate(new DataSourceConnectionProvider(dataSource),
            createMigrationsLoader(), options, System.out);
      } catch (MigrationException e) {
        throw new UncategorizedScriptException("Migration failed.", e.getCause());
      }
    }

    protected MigrationLoader createMigrationsLoader() {
      return new SpringMigrationLoader(resourcePatternResolver, scriptsDir, "utf-8", properties);
    }
  }

  private static class SpringMigrationLoader implements MigrationLoader {

    protected static final String BOOTSTRAP_SQL = "bootstrap.sql";

    protected static final String ONABORT_SQL = "onabort.sql";

    private ResourcePatternResolver resourcePatternResolver;
    private String path;
    private String charset;
    private Properties properties;

    public SpringMigrationLoader(
        ResourcePatternResolver resourcePatternResolver,
        String path,
        String charset,
        Properties properties) {
      this.resourcePatternResolver = resourcePatternResolver;
      this.path = path;
      this.charset = charset;
      this.properties = properties;
    }

    @Override
    public List<Change> getMigrations() {

      Collection<String> filenames = new TreeSet<>();

      for (Resource res : getResources("/*.sql")) {
        filenames.add(res.getFilename());
      }

      filenames.remove(BOOTSTRAP_SQL);
      filenames.remove(ONABORT_SQL);

      return filenames.stream()
          .map(this::parseChangeFromFilename)
          .collect(Collectors.toList());
    }

    @Override
    public Reader getScriptReader(Change change, boolean undo) {
      try {
        return getReader(change.getFilename(), undo);
      } catch (IOException e) {
        throw new MigrationException("Failed to read bootstrap script.", e);
      }
    }

    @Override
    public Reader getBootstrapReader() {
      try {
        return getReader(BOOTSTRAP_SQL, false);
      } catch (FileNotFoundException e) {
        // ignore
      } catch (IOException e) {
        throw new MigrationException("Failed to read bootstrap script.", e);
      }
      return null;
    }

    @Override
    public Reader getOnAbortReader() {
      try {
        return getReader(ONABORT_SQL, false);
      } catch (FileNotFoundException e) {
        // ignore
      } catch (IOException e) {
        throw new MigrationException("Failed to read onabort script.", e);
      }
      return null;
    }

    protected Resource getResource(String pattern) {
      return this.resourcePatternResolver.getResource(this.path + "/" + pattern);
    }

    protected Resource[] getResources(String pattern) {
      try {
        return this.resourcePatternResolver.getResources(this.path + pattern);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    protected Change parseChangeFromFilename(String filename) {
      try {
        String name = filename.substring(0, filename.lastIndexOf("."));

        int separator = name.indexOf("_");

        BigDecimal id = new BigDecimal(name.substring(0, separator));

        String description = name.substring(separator + 1).replace('_', ' ');

        Change change = new Change(id);
        change.setFilename(filename);
        change.setDescription(description);

        return change;

      } catch (Exception e) {
        throw new MigrationException("Error parsing change from file.  Cause: " + e, e);
      }
    }

    protected Reader getReader(String fileName, boolean undo) throws IOException {
      InputStream inputStream = getResource(fileName).getURL().openStream();
      return new MigrationReader(inputStream, charset, undo, properties);
    }
  }
}
