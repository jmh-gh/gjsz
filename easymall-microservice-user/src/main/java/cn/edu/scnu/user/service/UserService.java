package cn.edu.scnu.user.service;

import org.springframework.stereotype.Service;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
//import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.easymall.common.pojo.User;
import com.easymall.common.utils.MD5Util;
import com.easymall.common.utils.MapperUtil;

import cn.edu.scnu.user.mapper.UserMapper;
import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPool;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPool;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedis;

	public Integer checkUserName(String userName) {
		return userMapper.queryUserName(userName);
	}

	public void userSave(User user) {
		// user的数据填写完整，userId password加密
		user.setUserId(UUID.randomUUID().toString());
		// System.out.println(MD5Util.md5(user.getUserPassword()));
		user.setUserPassword(MD5Util.md5(user.getUserPassword()));
		userMapper.userSave(user);
	}

	/**
	 * 用户登录功能
	 */
	private ObjectMapper mapper = MapperUtil.MP;
//	@Autowired
//	private ShardedJedisPool pool;	

	public String doLogin(User user) {
//		ShardedJedis jedis = pool.getResource();
		try {
			// 对password做加密操作
			user.setUserPassword(MD5Util.md5(user.getUserPassword()));
			// 利用user数据查询数据库,判断登录是否合法
			User exist = userMapper.queryUserByUserIdAndUserPssword(user);
			if (exist == null) {// 登录失败,没有数据可以存储在redis
				// 返回一个""
				return "";
			} else {
				// 为了后续访问能够获取到user对象的数据
				// 需要创建一个key值,将userJson作为value
				// 存储在redis中,key值返回给前端,页面下次访问
				// 就可以携带
				// 生成一个cookie将要携带回去的ticket
				String ticket = UUID.randomUUID().toString();
				// jackson的代码.将已存在的exist用户对象转化成json
				// ObjectMapper mapper=new ObjectMapper();
				String userJson;
				// 将对象转化成json字符串
				userJson = mapper.writeValueAsString(exist);
				// 将userJson存储在redis中
				// Jedis jedis =new Jedis("192.168.126.151",6379);
//				jedis.set(ticket, userJson);
				 // 判断当前用户是否曾经有人登录过
				 String existTicket = jedis.get("user_logined" + exist.getUserId());
				 // 顶替实现.不允许前一个登录的人ticket存在
				 if (StringUtils.isNotEmpty(existTicket)) {
					 jedis.del(existTicket);
				 }
				 // 定义当前客户端登录的信息 userId:ticket
				 jedis.setex("user_logined" + exist.getUserId(), 60 * 30, ticket);
				 // 用户登录超时 30分钟
				 jedis.setex(ticket, 60 * 30, userJson);
				// //jedis.set(ticket, userJson);
				return ticket;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		// finally{
		// pool.returnResource(jedis);
		// }
	}

	public String queryUserJson(String ticket) {

		// Jedis jedis=new Jedis("192.168.126.151",6379);
		// return jedis.get(ticket);

//		ShardedJedis jedis = pool.getResource();
		String userJson = "";
		try {
			 // 首先判断超时剩余时间
			 Long leftTime = jedis.pttl(ticket);
			 // 少于十分钟时延长5分Z 长整型 所以加l
			 if (leftTime < 60 * 10 * 1000l) {
				 jedis.pexpire(ticket, leftTime + 60 * 5 * 1000);
			 }
			userJson = jedis.get(ticket);
			return userJson;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} 
//		finally {
//			pool.returnResource(jedis);
//		}
	}
}
