package cn.edu.scnu.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
@Configuration
@ConfigurationProperties(prefix="spring.redis.cluster")
public class RedisClusterConfig {
	private List<String> nodes;
	private Integer maxTotal;
	private Integer maxIdle;
	private Integer minIdle;
	//初始化JedisCluster的方法
	@Bean 
	public JedisCluster initJedisCluster(){
		//收集节点信息
		Set<HostAndPort> set=new HashSet<HostAndPort>();
		for (String node : nodes) {
			//node="192.168.126.151:8000"
			String host=node.split(":")[0];
			Integer port=Integer.
					parseInt(node.split(":")[1]);
			set.add(new HostAndPort(host,port));
		}
		GenericObjectPoolConfig config=new GenericObjectPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		return new JedisCluster(set,config);
	}
	//自动生成getter和setter方法
	public List<String> getNodes() {
		return nodes;
	}
	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
	public Integer getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}
	public Integer getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(Integer maxIdle) {
		this.maxIdle = maxIdle;
	}
	public Integer getMinIdle() {
		return minIdle;
	}
	public void setMinIdle(Integer minIdle) {
		this.minIdle = minIdle;
	}
}
