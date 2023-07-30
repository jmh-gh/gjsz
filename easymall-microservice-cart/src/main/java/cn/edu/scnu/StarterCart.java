package cn.edu.scnu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.edu.scnu.cart.mapper")
public class StarterCart {
    public static void main(String[] args) {
		SpringApplication.run(StarterCart.class, args);
	}
    //内部服务调用服务的RestTemplate对象
    @Bean
    @LoadBalanced
    public RestTemplate initCartRestTemplate(){
    	return new RestTemplate();
    }
}

