package test;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericRecordMap {

  private String tableName;
  private Map<String, Object> tableMap;
  private long id;
  private String pk;
}
