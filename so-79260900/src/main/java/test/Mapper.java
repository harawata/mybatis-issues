package test;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

public interface Mapper {

  void selectWithResultHandler(QueryRequest queryRequest, ResultHandler<Map<String, String>> resultHandler);

  List<TaskCountResponse> selectWithTypeHandler(QueryRequest queryRequest);

}
