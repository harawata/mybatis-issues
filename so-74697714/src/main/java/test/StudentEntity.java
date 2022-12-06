package test;

import java.sql.Timestamp;

public class StudentEntity {
  private Integer id;
  private String name;
  private Timestamp auditTimestamp;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Timestamp getAuditTimestamp() {
    return auditTimestamp;
  }

  public void setAuditTimestamp(Timestamp auditTimestamp) {
    this.auditTimestamp = auditTimestamp;
  }
}
