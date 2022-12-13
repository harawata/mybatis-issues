package service;

import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import test.User;

public class UserService2 {
  private SqlSessionFactory sqlSessionFactory;

  public List<BatchResult> insertUsers(List<User> users) {
    try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
      for (User user : users) {
        session.insert("mapper.Mapper.insertUser", user);
      }
      return session.flushStatements();
    }
  }

  public SqlSessionFactory getSqlSessionFactory() {
    return sqlSessionFactory;
  }

  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }
}
