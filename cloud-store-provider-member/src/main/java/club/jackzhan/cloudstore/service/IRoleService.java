package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.module.request.common.PageQueryRequest;
import club.jackzhan.cloudstore.module.request.role.RoleCreateRequest;
import club.jackzhan.cloudstore.module.request.role.RoleRemoveRequest;
import club.jackzhan.cloudstore.util.ResultResponse;
import club.jackzhan.cloudstore.module.request.member.MemberQueryRequest;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

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


    List<RoleDTO> getAllRoles();

    Page<RoleDTO> list(PageQueryRequest request);

    Boolean createRole(RoleCreateRequest request);

    Boolean removeRole(RoleRemoveRequest request);
}
