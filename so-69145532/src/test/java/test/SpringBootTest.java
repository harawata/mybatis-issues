package test;

import static org.junit.Assert.*;

import mapper.OrderMapper;
import org.apache.ibatis.cursor.Cursor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = Application.class)
public class SpringBootTest {

  @Autowired
  private OrderMapper orderMapper;

  @Test
  @Transactional(rollbackFor = Exception.class)
  public void testGetOrderList() {
    Map<String, Object> map = new HashMap<>(10);
    map.put("mainId", 1);
    Cursor<OrderRes> orderList = orderMapper.getOrderListCursorByMap(map);
    orderList.forEach(order -> {
      assertEquals(1, order.getFromResList().size());
    });

  }

}
