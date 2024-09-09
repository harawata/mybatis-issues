package test;

import org.apache.ibatis.session.ResultHandler;

public interface Mapper {

  Blog getBlog(Integer id);

  void getBlogs(ResultHandler<Blog> resultHandler);

}
