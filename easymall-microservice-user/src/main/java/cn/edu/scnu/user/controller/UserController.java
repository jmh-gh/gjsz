package cn.edu.scnu.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.wstx.util.StringUtil;
import com.easymall.common.pojo.User;
import com.easymall.common.utils.CookieUtils;
import com.easymall.common.vo.SysResult;
import com.netflix.discovery.converters.Auto;

import cn.edu.scnu.user.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
//	@Value("${spring.redis.cluster.nodes}")
//	private String url;
//	
//	@RequestMapping("pro")
//	public String cc() {
//		return url;
//	}

	@RequestMapping("user/manage/checkUserName")
	public SysResult checkUserName(String userName) {
		Integer exist = userService.checkUserName(userName);
		// 根据exist判断返回结果
		if (exist == 0) {
			return SysResult.ok();
		} else {
			return SysResult.build(201, "已存在", null);
		}
	}

	@RequestMapping("/user/manage/save")
	public SysResult userSave(User user) {
		Integer a = userService.checkUserName(user.getUserName());
		if (a > 0)
			return SysResult.build(201, "用户名已存在", null);
		try {
			userService.userSave(user);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, e.getMessage(), null);
		}
	}

	@RequestMapping("/user/manage/login")
	public SysResult doLogin(User user, HttpServletRequest request, HttpServletResponse response) {
		// 调用业务层确定合法并且存储数据
		String ticket = userService.doLogin(user);

		// 控制层将业务层存储登陆成功的rediskey值添加到cookie返回给前端
		if (StringUtils.isNotEmpty(ticket)) {
			// ticket非空，说明redis已经存好登录的查询结果
			// 将ticket作为cookie的值返回，cookie的名称调用cookie的工具类
			CookieUtils.setCookie(request, response, "EM_TICKET", ticket);
			return SysResult.ok();
		} else {
			return SysResult.build(201, "登录失败", null);
		}

	}

	@RequestMapping("/user/manage/query/{ticket}")
	public SysResult queryUserJson(@PathVariable String ticket) {
		String userJson = userService.queryUserJson(ticket);
		if (StringUtils.isNoneEmpty(userJson)) {
			return SysResult.build(200, "ok", userJson);
		} else {
			return SysResult.build(201, "", null);
		}
	}
}
