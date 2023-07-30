package cn.edu.scnu.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 实现秒杀需要的所有交换机
 * 队列的声明对象,在配置类中创建一些
 *spring维护的内存对象,这些对象会在系统调用
 *rabbitmq之前底层 declare出来
 * @author Administrator
 *
 */

@Configuration
public class RabbitmqConfig {
	public static final String exName="seckillEx";
	public static final String qName="seckillQueue";
	public static final String routingKey="seckill";
	//声明一个交换机
	@Bean
	public DirectExchange exInit(){
		return new DirectExchange(exName, false, false);
	}
	//声明队列
	@Bean
	public Queue queueInit(){
		return new Queue(qName, false, false, false);
	}
	//绑定关系
	@Bean
    public Binding bindInit(){
		//将队列携带路由key绑定交换机
		return  BindingBuilder.bind(queueInit()).to(exInit()).with(routingKey);
	}
}
