package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.module.request.common.BaseIdRequest;
import club.jackzhan.cloudstore.module.request.common.BaseIdsRequest;
import club.jackzhan.cloudstore.module.request.common.BaseRequest;
import club.jackzhan.cloudstore.module.request.member.CurrentMember;
import club.jackzhan.cloudstore.module.request.role.RoleCreateRequest;
import club.jackzhan.cloudstore.module.request.role.RoleUpdateRequest;
import club.jackzhan.cloudstore.service.IRoleService;
import club.jackzhan.cloudstore.util.RemoteCallUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 14:40
 *
 * @Author: JackZhan
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {


    @Autowired
    private RemoteCallUtil remoteCallUtil;

    @Override
    public ResultResponse getAllRoles() {
        return remoteCallUtil.sendGet("member.role.getAllRoles");
    }

    @Override
    public ResultResponse list(BaseRequest request) {
        return remoteCallUtil.sendGet("member.role.list", request);
    }

    @Override
    public ResultResponse createRole(RoleCreateRequest request, CurrentMember currentMember) {
        request.setUpdateUser(currentMember.getUsername());
        return remoteCallUtil.sendPost("member.role.createRole", request);
    }

    @Override
    public ResultResponse deleteRole(Integer id, CurrentMember currentMember) {
        BaseIdRequest<Integer> request = new BaseIdRequest<>();
        request.setUpdateUser(currentMember.getUsername());
        request.setId(id);
        return remoteCallUtil.sendPost("member.role.deleteRole", request);
    }

    @Override
    public ResultResponse batchDelete(BaseIdsRequest<Integer> request, CurrentMember currentMember) {
        request.setUpdateUser(currentMember.getUsername());
        return remoteCallUtil.sendPost("member.role.batchDelete", request);
    }

    @Override
    public ResultResponse updateRole(RoleUpdateRequest request, CurrentMember currentMember) {
        request.setUpdateUser(currentMember.getUsername());
        return remoteCallUtil.sendPost("member.role.updateRole", request);
    }

    @Override
    public ResultResponse getCheckRoles(BaseIdRequest<Integer> request) {
        return remoteCallUtil.sendGet("member.role.getCheckRoles", request);
    }

}
