package test;

import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

public interface Mapper {

  void selectWithResultHandler(QueryRequest queryRequest, ResultHandler<Map<String, String>> resultHandler);

}
