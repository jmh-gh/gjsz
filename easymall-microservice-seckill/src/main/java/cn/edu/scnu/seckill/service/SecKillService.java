package cn.edu.scnu.seckill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easymall.common.pojo.Seckill;
import com.easymall.common.pojo.Success;

import cn.edu.scnu.seckill.mapper.SecKillMapper;
import cn.edu.scnu.seckill.mapper.SucMapper;

@Service
public class SecKillService {
       
	@Autowired
	private SecKillMapper secKillMapper;
	@Autowired
	private SucMapper sucMapper;
	
	public List<Seckill> queryAll() {
		return secKillMapper.queryAll();
	
	}

	public Seckill queryOne(String seckillId) {
		long seckillId_long = Long.parseLong(seckillId);
		return secKillMapper.queryOne(seckillId_long);
	}

	public List<Success> querySuccess(String seckillId) {
		return sucMapper.querySuccess(seckillId);
		
	}
}
