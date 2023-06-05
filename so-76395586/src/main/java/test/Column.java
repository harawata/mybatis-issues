package test;

public class Column {
  private final String name;
  private final String type;
  private String defaultValue;

  public Column(String name, String type) {
    super();
    this.name = name;
    this.type = type;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Column [name=" + name + ", type=" + type + ", defaultValue=" + defaultValue + "]";
  }

}
