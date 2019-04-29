package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.util.ResultResponse;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 18:00
 *
 * @Author: JackZhan
 */
public interface IRoleService {

    /**
     * 根据用户的Id获取用户的角色
     * @param request
     * @return
     */
    ResultResponse getByMemberId(MemberQueryRequest request);
}
