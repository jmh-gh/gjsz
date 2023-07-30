package cn.edu.scnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class StarterEurekaServer02 {
	public static void main(String[] args) {
		//通过run方法讲反射信息发送给初始化过程
		//启动过程加载eureka相关依赖资源，自动配置eureka进程启动
		SpringApplication.run(StarterEurekaServer02.class, args);
	}
}
