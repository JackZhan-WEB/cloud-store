package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.entities.Role;
import club.jackzhan.cloudstore.enums.ErrorCodeEnum;
import club.jackzhan.cloudstore.enums.RoleStateEnum;
import club.jackzhan.cloudstore.enums.TrueFalseEnum;
import club.jackzhan.cloudstore.mapper.RoleMapper;
import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.service.IRoleService;
import club.jackzhan.cloudstore.util.AssertUtil;
import club.jackzhan.cloudstore.util.BeanUtils;
import club.jackzhan.cloudstore.util.ResultResponse;
import club.jackzhan.cloudstore.module.request.member.MemberQueryRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        AssertUtil.isNotNullForList(response,roles, ErrorCodeEnum.MEMBER_INFO_ERROR);
        return response;
    }


    @Override
    public List<RoleDTO> getAllRoles() {
        return BeanUtils.copyList(roleMapper.getAllRolesByStateAndType(RoleStateEnum.NORMAL.getCode(), TrueFalseEnum.TRUE.getCode()), RoleDTO.class);
    }
}
