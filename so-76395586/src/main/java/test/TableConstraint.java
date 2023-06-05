package test;

public class TableConstraint {
  private final String name;
  private final Schema schema;
  private final String tableName;

  public TableConstraint(String name, Schema schema, String tableName) {
    super();
    this.name = name;
    this.schema = schema;
    this.tableName = tableName;
  }

  public String getName() {
    return name;
  }

  public Schema getSchema() {
    return schema;
  }

  public String getTableName() {
    return tableName;
  }

  @Override
  public String toString() {
    return "TableConstraint [name=" + name + ", schema=" + schema + ", tableName=" + tableName + "]";
  }
}
