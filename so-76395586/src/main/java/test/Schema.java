package test;

public class Schema {
  private final String tableSchema;

  public Schema(String tableSchema) {
    super();
    this.tableSchema = tableSchema;
  }

  public String getTableSchema() {
    return tableSchema;
  }

  @Override
  public String toString() {
    return "Schema [tableSchema=" + tableSchema + "]";
  }
}
