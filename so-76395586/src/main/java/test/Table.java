package test;

import java.util.List;

public class Table {
  private final String tableName;
  private final Schema schema;
  private String createOptions;
  private String comment;
  private List<Column> columns;
  private List<TableConstraint> constraints;

  public Table(String tableName, Schema schema) {
    super();
    this.tableName = tableName;
    this.schema = schema;
  }

  public String getCreateOptions() {
    return createOptions;
  }

  public void setCreateOptions(String createOptions) {
    this.createOptions = createOptions;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }

  public List<TableConstraint> getConstraints() {
    return constraints;
  }

  public void setConstraints(List<TableConstraint> constraints) {
    this.constraints = constraints;
  }

  public String getTableName() {
    return tableName;
  }

  public Schema getSchema() {
    return schema;
  }

  @Override
  public String toString() {
    return "Table [tableName=" + tableName + ", schema=" + schema + ", createOptions=" + createOptions + ", comment="
        + comment + ", columns=" + columns + "]";
  }
}
