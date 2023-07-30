package cn.edu.scnu.seckill.consumer;

import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.easymall.common.pojo.Success;

import cn.edu.scnu.seckill.config.RabbitmqConfig;
import cn.edu.scnu.seckill.mapper.SecKillMapper;
import cn.edu.scnu.seckill.mapper.SucMapper;
import redis.clients.jedis.JedisCluster;

@Component
public class SeckillConsumer {
	@Autowired
	private SecKillMapper secKillMapper;
	@Autowired
	private SucMapper sucMapper;
	@Autowired
	private JedisCluster cluster;
	// 定义一个处理消息的逻辑代码
	// 声明式注解,底层实现异步监听
	@RabbitListener(queues = RabbitmqConfig.qName)
	public void processMsg(String msg) {
		// 假设当前方法的参数就是接受的
		// rabbitmq的某个队列的消息
		System.out.println("消费端接收消息:" + msg);
		
//		// msg包含了用户信息,和商品信息
//		// 根据商品信息,秒杀的减库存。
//		// 将用户信息和秒杀商品入库保存作为成功秒杀数据 success
//		// 将用户userId和seckillId分离开
//		String userId = msg.split("/")[0];
//		Long seckillId = Long.parseLong(msg.split("/")[1]);
//		// 执行减库存 对当前商品的库存减1,条件number>0 nowTime<endTime,nowTime>startTime
//		Date nowTime = new Date();
//		int result = secKillMapper.updateNum(seckillId, nowTime);
//		if (result == 0) {// 库存没有减成功
//			// 说明卖完了,处理失败逻辑
//			System.out.println(userId + "用户秒杀失败");
//			return;
//		}
//		// 封装成功的数据success对象,新增到success表格
//		Success success = new Success();
//		success.setCreateTime(nowTime);
//		success.setSeckillId(seckillId);
//		success.setUserId(userId);
//		success.setState(1);
//		sucMapper.saveSuc(success);
		
		// msg包含了用户信息,和商品信息
		// 根据商品信息,秒杀的减库存 100-99 seckill
		// 将用户信息和秒杀商品入库保存作为成功秒杀数据 success
		// 将将用户userPhone和seckillId分离开

		// 判断redis数据获取成功的情况下
		
		 if(!cluster.exists("opposeckill")){ //opposeckill redis中oppo手机的存量的key
			 //vlaue个数对应存量个数
			 System.out.println("该商品已经秒杀完毕！");
			 return;
		 }
		 String rpop=cluster.rpop("opposeckill");
		 if(rpop==null){ //当前消费者没有从redis的list获取任何rpop的数据,说明list中的所有元素为空,卖完了
			 System.out.println("该商品已经秒杀完毕");
			 return;
		 }
		 String userId=msg.split("/")[0];
		 Long seckillId=Long.parseLong(msg.split("/")[1]);
		 //执行减库存 对当前商品的库存减一，条件是number>0 startTime<nowTime<endTime
		 Date nowTime=new Date();
		
		 int result=secKillMapper.updateNum(seckillId,nowTime);
		 if (result==0){//说明卖完了
			 System.out.println(userId+"用户秒杀失败");
			 return;
		 }
		 //封装成功的数据success对象，新增到success表格
		 Success success=new Success();
		 success.setCreateTime(nowTime);
		 success.setSeckillId(seckillId);
		 success.setState(1);
		 success.setUserId(userId);
		 try{
			 sucMapper.saveSuc(success);
		 }catch(Exception e){
			 e.printStackTrace();
			 return;
		 }

	}

}
