package cn.edu.scnu.cart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.easymall.common.pojo.Cart;
import com.easymall.common.pojo.Product;

import cn.edu.scnu.cart.mapper.CartMapper;



@Service
public class CartService {

	@Autowired
	private CartMapper cartMapper;
	
	public List<Cart> queryMyCart(String userId) {
		return cartMapper.queryMyCart(userId);
		 
	}
	@Autowired
	private RestTemplate restTemplate;
	public void saveCart(Cart cart) {
		//cart中只有userId productId num
		//判断是否已存在于购物车中
		Cart exist=cartMapper.queryOne(cart);
		if(exist!=null){//表示数据已存在
			//将新增的num加上已存在的num更新到数据库表格
			//exist作为参数先更新内存的数据
			exist.setNum(exist.getNum()+cart.getNum());
			cartMapper.updateNum(exist);
		}else{//表示购物车中不存在数据
		     //需要补充商品name,商品价钱,商品图片    。。需要向product服务请求数据
			Product product=restTemplate.getForObject("http://productservice/product/manage/item/"+cart.getProductId(), Product.class);
			cart.setProductName(product.getProductName());
			cart.setProductPrice(product.getProductPrice());
			cart.setProductImage(product.getProductImgurl());
			cartMapper.saveCart(cart);
		}
	}
	public void updateCart(Cart cart) {
		cartMapper.updateNum(cart);
	}
	public void deleteCart(Cart cart) {
		cartMapper.deleteCart(cart);	
	}

}
