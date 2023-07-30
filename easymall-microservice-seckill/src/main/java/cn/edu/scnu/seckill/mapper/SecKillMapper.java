package cn.edu.scnu.seckill.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.easymall.common.pojo.Seckill;
import com.easymall.common.pojo.Success;

public interface SecKillMapper {

	List<Seckill> queryAll();

	Seckill queryOne(long seckillId);

	int update(Long seckillId, Date nowTime);

	int updateNum(@Param("seckillId")Long seckillId, @Param("nowTime")Date nowTime);


}
