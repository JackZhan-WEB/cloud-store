package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.util.PageBean;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.util.ResultResponse;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 18:00
 *
 * @Author: JackZhan
 */
public interface IMemberService {

    /**
     * 根据用户的登陆名获取用户信息
     * @param request
     * @return
     */
    String getMember(MemberQueryRequest request);

    PageBean list(MemberQueryRequest request);

    List<RoleDTO> getAllRoles();

    Boolean createUser(MemberQueryRequest request);
}
