package test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import mapper.BaseEntityMapper;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = Application.class)
public class SpringBootTest {

  @Autowired
  private BaseEntityMapper userMapper;

  @Test
  public void testProcedure() {
    BaseEntity param = new BaseEntity();
    param.setName("John");
    param.setFilename(new String[] { "a", "b" });
    userMapper.add(param);

    // Verify OUT param
    assertArrayEquals(new String[] { "1", "2" }, param.getOutparam());

    // Verify inserted rows
    List<Map<String, Object>> rows = userMapper.selectTest();
    assertEquals(2, rows.size());
    Map<String, Object> row1 = rows.get(0);
    assertEquals("John", row1.get("NAME"));
    assertEquals("a", row1.get("FILENAME"));
    Map<String, Object> row2 = rows.get(1);
    assertEquals("John", row2.get("NAME"));
    assertEquals("b", row2.get("FILENAME"));
  }

}
