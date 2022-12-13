package service;

import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import test.User;

public class UserService {
  private SqlSession batchSqlSession;

  public User getUser(Integer id) {
    return batchSqlSession.selectOne("mapper.Mapper.getUser", id);
  }

  @Transactional
  public List<BatchResult> insertUsers(List<User> users) {
    for (User user : users) {
      batchSqlSession.insert("mapper.Mapper.insertUser", user);
    }
    return batchSqlSession.flushStatements();
  }

  public List<BatchResult> insertUsersWithoutTransactional(List<User> users) {
    for (User user : users) {
      batchSqlSession.insert("mapper.Mapper.insertUser", user);
    }
    return batchSqlSession.flushStatements();
  }

  public SqlSession getBatchSqlSession() {
    return batchSqlSession;
  }

  public void setBatchSqlSession(SqlSession batchSqlSession) {
    this.batchSqlSession = batchSqlSession;
  }
}
