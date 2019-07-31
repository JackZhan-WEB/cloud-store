package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.entities.Role;
import club.jackzhan.cloudstore.enums.ErrorCodeEnum;
import club.jackzhan.cloudstore.enums.RoleStateEnum;
import club.jackzhan.cloudstore.enums.TrueFalseEnum;
import club.jackzhan.cloudstore.mapper.RoleMapper;
import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.module.request.common.BaseIdRequest;
import club.jackzhan.cloudstore.module.request.common.BaseIdsRequest;
import club.jackzhan.cloudstore.module.request.common.PageQueryRequest;
import club.jackzhan.cloudstore.module.request.member.MemberQueryRequest;
import club.jackzhan.cloudstore.module.request.role.RoleCreateRequest;
import club.jackzhan.cloudstore.module.request.role.RoleUpdateRequest;
import club.jackzhan.cloudstore.service.IRoleService;
import club.jackzhan.cloudstore.util.AssertUtil;
import club.jackzhan.cloudstore.util.BeanUtils;
import club.jackzhan.cloudstore.util.ResultResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 18:01
 *
 * @Author: JackZhan
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public ResultResponse getByMemberId(MemberQueryRequest request) throws RuntimeException {
        List<Role> roles = roleMapper.getByMemberId(request.getMemberId());
        ResultResponse response = ResultResponse.create(roles);
        AssertUtil.isNotNullForList(response, roles, ErrorCodeEnum.MEMBER_INFO_ERROR);
        return response;
    }


    @Override
    public List<RoleDTO> getAllRoles() {
        return BeanUtils.copyList(roleMapper.getAllRolesByStateAndType(RoleStateEnum.NORMAL.getCode(), TrueFalseEnum.TRUE.getCode()), RoleDTO.class);
    }

    @Override
    public Page<RoleDTO> list(PageQueryRequest request) {
        Page<RoleDTO> page = new Page<>();
        page.setSize(request.getPageSize());
        page.setCurrent(request.getCurrentPage());
        List<RoleDTO> roles = BeanUtils.copyList(roleMapper.selectPage(page, new EntityWrapper<Role>().eq("state", RoleStateEnum.NORMAL.getCode())), RoleDTO.class);
        page.setRecords(roles);
        return page;
    }

    @Override
    public Boolean createRole(RoleCreateRequest request) {
        Date now = new Date();
        Role role = new Role();
        role.setName(request.getName())
                .setCode(request.getCode())
                .setDescription(request.getDescription())
                .setState(RoleStateEnum.NORMAL.getCode())
                .setType(TrueFalseEnum.TRUE.getCode())
                .setCreateTime(now)
                .setUpdateTime(now)
                .setUpdateUser(request.getUpdateUser());
        //保存角色基础信息
        int insert = roleMapper.insert(role);
        if (insert <= 0) {
            return Boolean.FALSE;
        }

        //保存角色的权限信息
        return roleMapper.insertRolePerms(role.getId(), request.getPermChecks()) > 0;
    }

    @Override
    public Boolean deleteRole(BaseIdRequest<Integer> request) {
        Integer id = request.getId();
        BaseIdsRequest<Integer> idsRequest = new BaseIdsRequest<>();
        idsRequest.setIds(Arrays.asList(id));
        return batchDelete(idsRequest);
    }

    @Override
    public Boolean batchDelete(BaseIdsRequest<Integer> request) {
        List<Integer> ids = request.getIds();
        if (roleMapper.deleteBatchIds(ids) > 0) {
            roleMapper.deleteMemberRole(ids);
            roleMapper.deleteRolePermission(ids);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean updateRole(RoleUpdateRequest request) {
        //修改
        Integer[] permChecks = request.getPermChecks();
        if (permChecks != null && permChecks.length != 0) {
            roleMapper.deleteRolePermission(Arrays.asList(request.getId()));
            roleMapper.insertRolePerms(request.getId(), request.getPermChecks());
        }
        Integer[] roleChecks = request.getRoleChecks();
        if (roleChecks != null && roleChecks.length != 0) {
            roleMapper.deleteParentRoleById(request.getId());
            roleMapper.insertParentRole(request.getId(), request.getRoleChecks());
        }
        return roleMapper.updateById(BeanUtils.copyProperties(request,Role.class)) > 0;
    }

    @Override
    public List<Integer> getCheckRoles(BaseIdRequest request) {
        return roleMapper.getCheckRoles(request.getId());
    }
}
