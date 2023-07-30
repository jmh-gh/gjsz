package cn.edu.scnu.order.service;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymall.common.pojo.Order;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.order.mapper.OrderMapper;

@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;

	public void saveOrder(Order order) {
		//补充数据
		order.setOrderId(UUID.randomUUID().toString());
		order.setOrderTime(new Date());
//		order.setOrderPaystate(0);
		orderMapper.saveOrder(order);
	}

	public List<Order> queryOrder(String userId) {
		return orderMapper.queryOrder(userId);
	}

	public void deleteOrder(String orderId) {
		 orderMapper.deleteOrder(orderId);
	}
}
