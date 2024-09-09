package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class BlogResultHandler implements ResultHandler<Blog> {
  List<Blog> blogs = new ArrayList<>();
  Map<Integer, Author> authors = new HashMap<>();

  @Override
  public void handleResult(ResultContext<? extends Blog> resultContext) {
    Blog blog = resultContext.getResultObject();
    Author blogAuthor = blog.getAuthor();
    replaceAuthorIfCached(blogAuthor, blog::setAuthor);
    blog.getPosts().forEach(post -> {
      replaceAuthorIfCached(post.getAuthor(), post::setAuthor);
    });
    blogs.add(blog);
  }

  private void replaceAuthorIfCached(Author author, Consumer<Author> setter) {
    Author cachedAuthor = authors.putIfAbsent(author.getId(), author);
    if (cachedAuthor != null) {
      setter.accept(cachedAuthor);
    }
  }

  public List<Blog> getBlogs() {
    return blogs;
  }
}
