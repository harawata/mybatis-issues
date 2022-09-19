package test;

import java.util.List;

public interface Mapper {
  DivideOut selectMultiple();

  List<List<?>> selectTwoLists();
}
