package test;

public interface Mapper {
  User getUser(Integer id);

  void insertUser(User user);
}
