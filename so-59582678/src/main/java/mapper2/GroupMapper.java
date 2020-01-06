package mapper2;

import test.Group;

public interface GroupMapper {
  Group getGroup(Integer id);

  void insertGroup(Group user);
}
