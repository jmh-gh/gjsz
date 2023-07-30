package cn.edu.scnu.cart.mapper;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.easymall.common.pojo.Cart;
public interface CartMapper {

	List<Cart> queryMyCart(String userId);

	Cart queryOne(Cart cart);

	void updateNum(Cart exist);

	void saveCart(Cart cart);

	void deleteCart(Cart cart);

}
