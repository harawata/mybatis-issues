package test;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

public class SpringBeanObjectFactory implements ObjectFactory {

  private final ObjectFactory delegate = new DefaultObjectFactory();

  private final ApplicationContext applicationContext;

  public SpringBeanObjectFactory(ApplicationContext applicationContext) {
    super();
    this.applicationContext = applicationContext;
  }

  @Override
  public <T> T create(Class<T> type) {
    try {
      return applicationContext.getBean(type);
    } catch (NoSuchBeanDefinitionException e) {
      return delegate.create(type);
    }
  }

  @Override
  public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
    try {
      return applicationContext.getBean(type, constructorArgs.toArray());
    } catch (NoSuchBeanDefinitionException e) {
      return delegate.create(type, constructorArgTypes, constructorArgs);
    }
  }

  @Override
  public <T> boolean isCollection(Class<T> type) {
    return delegate.isCollection(type);
  }

  @Override
  public void setProperties(Properties properties) {
  }
}
