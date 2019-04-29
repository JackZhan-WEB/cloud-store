package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.mapper.MemberMapper;
import club.jackzhan.cloudstore.mapper.PermissionsMapper;
import club.jackzhan.cloudstore.mapper.RoleMapper;
import club.jackzhan.cloudstore.module.dto.MemberDTO;
import club.jackzhan.cloudstore.module.dto.PermissionsDTO;
import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.BeanUtils;
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
public class MemberServiceImpl implements IMemberService {

    @Resource
    private MemberMapper memberMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PermissionsMapper permissionsMapper;

    @Override
    public String getMember(MemberQueryRequest request) {
        //获取member
        MemberDTO memberDTO = BeanUtils.copyProperties(memberMapper.getMember(request.getLoginName()), MemberDTO.class);
        if (memberDTO != null) {
            //获取roles
            List<RoleDTO> roles = BeanUtils.copyList(roleMapper.getByMemberId(memberDTO.getId()), RoleDTO.class);
            memberDTO.setRoles(roles);
            //获取permissions
            for (RoleDTO role : roles) {
                role.setPermissions(BeanUtils.copyList(permissionsMapper.getByRoleId(role.getId()), PermissionsDTO.class));
            }
        }
        return BeanUtils.beanToJsonStringWithDateFormat(memberDTO);
    }
}
