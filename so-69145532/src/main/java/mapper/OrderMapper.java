package mapper;

import test.User;


public interface OrderMapper {

  Cursor<OrderRes> getOrderListCursorByMap(@Param("map") Map<String, Object> map);
}
