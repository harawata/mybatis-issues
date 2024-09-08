package test;

public class Post {
  private Integer id;
  private String subject;
  private Author author;
  private Blog blog;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public Blog getBlog() {
    return blog;
  }

  public void setBlog(Blog blog) {
    this.blog = blog;
  }

  @Override
  public String toString() {
    return "Post [id=" + id + ", subject=" + subject + ", author=" + author + "]";
  }
}
