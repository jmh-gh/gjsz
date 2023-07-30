package cn.edu.scnu.order.mapper;

import java.util.List;

import com.easymall.common.pojo.Order;

public interface OrderMapper {
	void saveOrder(Order order);

	List<Order> queryOrder(String userId);

	void deleteOrder(String orderId);


}
