package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.constant.Constants;
import club.jackzhan.cloudstore.module.request.member.MemberQueryRequest;
import club.jackzhan.cloudstore.service.LoginService;
import club.jackzhan.cloudstore.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-19 15:44
 *
 * @Author: JackZhan
 */
@RestController
@RequestMapping("/login")
@Api(value = "Login", tags = {"Login"}, description = "登陆相关")
public class LoginController {


	@Resource
	private LoginService loginService;
	/**
	 * 登录
	 */
	@PostMapping("/auth")
	@ApiOperation(value = "登录")
	public ResultResponse authLogin(@RequestBody MemberQueryRequest request) {
		return loginService.authLogin(request);
	}

	/**
	 * 查询当前登录用户的信息
	 */
	@PostMapping("/getInfo")
	@ApiOperation(value = "查询当前登录用户的信息")
	public ResultResponse getInfo(HttpServletRequest request) {
		return loginService.getInfo(request.getHeader(Constants.TOKEN));
	}

	/**
	 * 登出
	 */
	@PostMapping("/logout")
	@ApiOperation(value = "登出")
	public ResultResponse logout() {
		return loginService.logout();
	}
}
