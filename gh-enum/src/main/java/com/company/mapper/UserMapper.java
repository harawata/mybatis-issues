package com.company.mapper;

import java.util.List;

import com.company.model.User;

public interface UserMapper {
  User getUser(Integer id);

  int insertUser(User user);
  
  List<User> findAll();
  
}
