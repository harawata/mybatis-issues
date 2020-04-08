package test;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mapper.UserMapper;

@Service
public class UserService {

  @Autowired
  @Qualifier("batchSqlSession")
  private SqlSession sqlSession;

  @Transactional
  public List<BatchResult> updateUsers(List<User> users) {
    List<BatchResult> results = new ArrayList<>();
    final int batchSize = 100;
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    final int size = users.size();
    for (int i = 0; i < size;) {
      userMapper.update(users.get(i));
      i++;
      if (i % batchSize == 0 || i == size) {
        results.addAll(sqlSession.flushStatements());
        sqlSession.clearCache();
      }
    }
    return results;
  }

  @Transactional
  public List<BatchResult> insertUsers(List<User> users) {
    List<BatchResult> results = new ArrayList<>();
    final int batchSize = 100;
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    userMapper.createTempTable();
    final int size = users.size();
    for (int i = 0; i < size;) {
      userMapper.insertTemp(users.get(i));
      i++;
      if (i % batchSize == 0 || i == size) {
        results.addAll(sqlSession.flushStatements());
        sqlSession.clearCache();
      }
    }
    userMapper.updateUsers();
    return results;
  }
}
