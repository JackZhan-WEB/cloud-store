package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.constant.Constants;
import club.jackzhan.cloudstore.module.request.BaseRequest;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.LoginService;
import club.jackzhan.cloudstore.util.ResultResponse;
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
public class LoginController {


	@Resource
	private LoginService loginService;
	/**
	 * 登录
	 */
	@PostMapping("/auth")
	public ResultResponse authLogin(@RequestBody MemberQueryRequest request) {
		return loginService.authLogin(request);
	}

	/**
	 * 查询当前登录用户的信息
	 */
	@PostMapping("/getInfo")
	public ResultResponse getInfo(HttpServletRequest request) {
		return loginService.getInfo(request.getHeader(Constants.TOKEN));
	}

	/**
	 * 登出
	 */
	@PostMapping("/logout")
	public ResultResponse logout() {
		return loginService.logout();
	}
}
