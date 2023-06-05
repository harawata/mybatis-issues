package test;

import java.util.List;

public interface Mapper {
  List<Table> selectTable(String tableName);
}
