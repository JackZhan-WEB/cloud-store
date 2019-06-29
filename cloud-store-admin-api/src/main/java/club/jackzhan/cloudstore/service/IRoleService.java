package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.request.common.BaseRequest;
import club.jackzhan.cloudstore.module.request.member.CurrentMember;
import club.jackzhan.cloudstore.module.request.role.RoleCreateRequest;
import club.jackzhan.cloudstore.module.request.role.RoleRemoveRequest;
import club.jackzhan.cloudstore.util.ResultResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 14:39
 *
 * @Author: JackZhan
 */
public interface IRoleService {

    ResultResponse getAllRoles();

    ResultResponse list(BaseRequest request);

    ResultResponse createRole(RoleCreateRequest request, CurrentMember currentMember);

    ResultResponse removeRole(RoleRemoveRequest request, CurrentMember currentMember);
}
