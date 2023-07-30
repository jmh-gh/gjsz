package cn.edu.scnu.seckill.mapper;

import java.util.List;

import com.easymall.common.pojo.Success;

public interface SucMapper {

	void saveSuc(Success success);

	List<Success> querySuccess(String seckillId);

}
