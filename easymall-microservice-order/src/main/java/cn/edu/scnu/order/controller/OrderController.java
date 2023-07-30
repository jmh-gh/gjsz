package cn.edu.scnu.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easymall.common.pojo.Order;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.order.service.OrderService;

@RestController
@RequestMapping("order/manage")
public class OrderController {
	@Autowired
	private OrderService orderService;	
	/*js请求地址	http://www.easymall.com/order/save
	后台接收	/order/manage/save
	请求方式	Post
	请求参数	Order order,其中缺少order的id值
	返回数据	返回SysResult对象的json,其结构:
	Integer status; 200表示成功,其他表示失败
	String msg;成功返回 “ok”,失败返回其他信息
	Object data;根据需求携带其他数据*/
	//订单数据新增
	@RequestMapping("save")
	public SysResult saveOrder(Order order){
		try{
			orderService.saveOrder(order);
			return SysResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);
		}
	}
	/*js请求地址	http://www.easymall.com/order/query/{userId}
	后台接收	/order/manage/query/{userId}
	请求方式	get
	请求参数	路径参数String userId
	返回数据	List<Order>数据,根据长度判断查询是否成功,如果长度为0可能是未登录也可能是就是没有数据.
	 */
	//订单查询
	@RequestMapping("query/{userId}")
	public List<Order> queryOrder(@PathVariable String userId){
		return orderService.queryOrder(userId);
	}
	/*js请求地址	http://www.easymall.com/order/query/{userId}
	后台接收	/order/manage/query/{userId}
	请求方式	get
	请求参数	路径参数String userId
	返回数据	List<Order>数据,根据长度判断查询是否成功,如果长度为0可能是未登录也可能是就是没有数据.
	 */
	@RequestMapping("delete/{orderId}")
	public SysResult deleteOrder(@PathVariable String orderId){
		try {
			orderService.deleteOrder(orderId);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);
		}
	}
}
