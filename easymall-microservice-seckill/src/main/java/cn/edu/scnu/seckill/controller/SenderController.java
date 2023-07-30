package cn.edu.scnu.seckill.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.scnu.seckill.config.RabbitmqConfig;


@RestController
public class SenderController {

	 @Autowired
	 private RabbitTemplate  client;
	 //访问send接口
	 @RequestMapping("send")
	 public String sendMsg(String msg){
		 //object消息本身		 
		 client.convertAndSend(RabbitmqConfig.exName, RabbitmqConfig.routingKey, msg);
		 return "sucess";
	 }
}
