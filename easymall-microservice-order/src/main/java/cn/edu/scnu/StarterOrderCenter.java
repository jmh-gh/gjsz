package cn.edu.scnu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("cn.edu.scnu.order.mapper")
@EnableEurekaClient
public class StarterOrderCenter {
	public static void main(String[] args) {
			SpringApplication.run(StarterOrderCenter.class, args);
	}
}
