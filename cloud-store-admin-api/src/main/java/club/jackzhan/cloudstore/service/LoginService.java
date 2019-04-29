package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.util.ResultResponse;

/**
 * @author: hxy
 * @description: 登录Service
 * @date: 2017/10/24 11:02
 */
public interface LoginService {
	/**
	 * 登录表单提交
	 */
	ResultResponse authLogin(MemberQueryRequest request);


	/**
	 * 查询当前登录用户的权限等信息
	 */
	ResultResponse getInfo();

	/**
	 * 退出登录
	 */
	ResultResponse logout();
}
