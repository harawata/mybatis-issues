package component;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class User {
  // Just to verify @Autowired is working
  @Autowired
  private DataSource dataSource;

  public DataSource getDataSource() {
    return dataSource;
  }

  public User(Integer id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  private Integer id;
  private String name;

  public Integer getId() {
    return id;
  }
  public String getName() {
    return name;
  }
}
