package test;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

public class SqlDataUser extends User implements SQLData {

  public SqlDataUser() {
    super();
  }

  public SqlDataUser(Integer id, String name) {
    super(id, name);
  }

  @Override
  public String getSQLTypeName() throws SQLException {
    return "S_USER_OBJ";
  }

  @Override
  public void readSQL(SQLInput stream, String typeName) throws SQLException {
    this.setId(stream.readInt());
    this.setName(stream.readString());
  }

  @Override
  public void writeSQL(SQLOutput stream) throws SQLException {
    stream.writeInt(this.getId());
    stream.writeString(this.getName());
  }

}
