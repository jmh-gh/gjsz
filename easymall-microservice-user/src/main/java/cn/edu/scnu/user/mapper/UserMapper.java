package cn.edu.scnu.user.mapper;

import com.easymall.common.pojo.User;

public interface UserMapper {

	Integer queryUserName(String userName);

	void userSave(User user);

	User queryUserByUserIdAndUserPssword(User user);

}
