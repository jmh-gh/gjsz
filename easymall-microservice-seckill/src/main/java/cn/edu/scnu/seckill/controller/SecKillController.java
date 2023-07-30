package cn.edu.scnu.seckill.controller;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easymall.common.pojo.Seckill;
import com.easymall.common.pojo.Success;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.seckill.config.RabbitmqConfig;
import cn.edu.scnu.seckill.service.SecKillService;

@RestController

public class SecKillController {
	@Autowired
	private SecKillService secKillService;
	/*读取秒杀表格商品list
	 * 
	 * js请求地址	http://www.easymall/seckills/list
		后台接收	seckill/manage/list
		请求方式	get
		请求参数	空的
		返回数据	List<Seckill>的查询数据*/
     @RequestMapping("seckill/manage/list")
     public List<Seckill> queryAll(){
    	return  secKillService.queryAll();
     }
     /*单个秒杀商品信息详情查询
      * 
      * js请求地址	http://www.easymall/seckills/detail?seckillId=1
    	 后台接收	/seckill/manage/detail?seckillId=1
    	 请求方式	Get
    	 请求参数	Long seckillId
    	 返回数据	Seckill根据id的查询对象*/
     @RequestMapping("seckill/manage/detail")
     public Seckill queryOne(String seckillId){
    	 return secKillService.queryOne(seckillId);
     }     
     @Autowired
     private RabbitTemplate client;
     /*接收秒杀商品的请求
      * 
      * js请求地址	http://www.easymall/seckills/{seckillId}
    	 后台接收	/seckill/manage/{seckillId}
    	 请求方式	Get
    	 请求参数	Long seckillId 路径传参
    	 返回数据	SysResult的返回对象
    	 Integer status 200表示秒杀成功
    	 String msg:ok表示成功
    	 Object data:其他数据
    	 备注	根据cookie获取用户信息,拼接用户数据到消息中,绑定一个秒杀的商品*/
     @RequestMapping("/seckill/manage/{seckillId}")
     public SysResult startSeckill(@PathVariable Long seckillId){
    	 //确定用户身份，userid username  userphone 但是前端js没有实现登陆才能秒杀的功能
    	 //所以随机生成一个userphone来代表user
    	 String userPhone="180888"+RandomUtils.nextInt(10000, 99999);
			//发送消息 确定用户身份,秒杀商品消息
			//一个用户最多可以秒杀3个商品?怎么做?list hash set
			//list的llen hash hlen set 的scart userId/userPhone作为key的核心
			//seckillId做value中数据 redis的锁 pipeLine
			String msg=userPhone+"/"+seckillId;
			client.convertAndSend(RabbitmqConfig.exName, RabbitmqConfig.routingKey, msg);
			return SysResult.ok();    	 
     }    
     /*展示成功者
      * 
      * js请求地址	http://www.easymall/seckills/{seckillId}/userPhone
    	 后台接收	/seckill/manage/{seckillId}/userPhone
    	 请求方式	Get
    	 请求参数	Long seckillId 路径传参
    	 返回数据	List<Success>对象,将成功者信息封装返回页面*/
     @RequestMapping("seckill/manage/{seckillId}/userPhone")
     public List<Success> querySuccess(@PathVariable String seckillId){
    	 return secKillService.querySuccess(seckillId);    	 
     }
}
