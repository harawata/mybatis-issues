package test;

import java.util.List;

public interface Mapper {
  List<StoredProcOutput> callStoredProcedure(StoredProcInput inParam);
}
