package mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;
import test.OrderRes;

import java.util.Map;


public interface OrderMapper {

  Cursor<OrderRes> getOrderListCursorByMap(@Param("map") Map<String, Object> map);
}
