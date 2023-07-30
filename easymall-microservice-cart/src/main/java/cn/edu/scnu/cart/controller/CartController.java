package cn.edu.scnu.cart.controller;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easymall.common.pojo.Cart;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.cart.service.CartService;

@RestController
@RequestMapping("cart/manage")
public class CartController {
	@Autowired
	private CartService cartService;
	/*http://www.easymall.com/cart/query?userId=**
	/cart/manage/query?userId=**
	Get
	String userId
	返回List<Cart>数据*/
	@RequestMapping("query")
	public List<Cart> queryMyCart(String userId){
		//对userId做非空判断
		if(!StringUtils.isNotEmpty(userId)){
			return null;//userId为空不做后续调用
		}
		return cartService.queryMyCart(userId);
	}
	/*js请求地址	http://www.easymall.com/cart/save
	后台接收	/cart/manage/save
	请求方式	post
	请求参数	Cart cart,缺少商品name,商品价钱,商品图片
	返回数据	返回SysResult对象的json,其结构:
	Integer status; 200表示成功,其他表示失败
	String msg;成功返回 “ok”,失败返回其他信息
	Object data;
	备注	需要调用商品查询功能将其他三个字段补齐,并且需要逻辑判断新增购物车是更新num还是新增*/
	@RequestMapping("save")
	public SysResult saveCart(Cart cart){
		try {
			cartService.saveCart(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
		
	}
	/*js请求地址	http://www.easymall.com/cart/update
	后台接收	/cart/manage/update
	请求方式	get
	请求参数	Cart cart,具有三个属性userId,productid,num
	返回数据	返回SysResult对象的json,其结构:
	Integer status; 200表示成功,其他表示失败
	String msg;成功返回 “ok”,失败返回其他信息
	Object data;*/
	@RequestMapping("update")
	public SysResult updateCart(Cart cart){
		try {
			cartService.updateCart(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
	}
	/*js请求地址	http://www.easymall.com/cart/delete
	后台接收	/cart/manage/delete
	请求方式	get
	请求参数	Cart cart,具有两个属性userId,productid
	返回数据	返回SysResult对象的json,其结构:
	Integer status; 200表示成功,其他表示失败
	String msg;成功返回 “ok”,失败返回其他信息
	Object data;*/
	@RequestMapping("delete")
	public SysResult deleteCart(Cart cart){
		try {
			cartService.deleteCart(cart);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "", null);
		}
	}
}
