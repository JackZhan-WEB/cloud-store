package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.mapper.MemberMapper;
import club.jackzhan.cloudstore.mapper.MenuMapper;
import club.jackzhan.cloudstore.mapper.PermissionsMapper;
import club.jackzhan.cloudstore.mapper.RoleMapper;
import club.jackzhan.cloudstore.module.dto.MemberDTO;
import club.jackzhan.cloudstore.module.dto.MenuDTO;
import club.jackzhan.cloudstore.module.dto.PermissionsDTO;
import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.module.entities.Member;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.BeanUtils;
import club.jackzhan.cloudstore.util.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionsMapper permissionsMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public String getMember(MemberQueryRequest request) {
        //获取member
        MemberDTO memberDTO = BeanUtils.copyProperties(memberMapper.getMember(request.getLoginName()), MemberDTO.class);
        if (memberDTO != null) {
            //获取roles
            List<RoleDTO> roles = BeanUtils.copyList(roleMapper.getByMemberId(memberDTO.getId()), RoleDTO.class);
            memberDTO.setRoles(roles);
            List<MenuDTO> menus = new ArrayList<>();
            StringBuilder roleIds = new StringBuilder();
            for (RoleDTO role : roles) {
                roleIds.append(role.getId());
                roleIds.append(",");
            }
            if (roleIds.length() > 0) {
                roleIds.deleteCharAt(roleIds.length() - 1);
            }
            //获取permissions
            List<PermissionsDTO> permissions = BeanUtils.copyList(permissionsMapper.getByRoleIds(roleIds.toString()), PermissionsDTO.class);
            memberDTO.setPermissions(permissions);
            StringBuilder permissionIds = new StringBuilder();
            for (PermissionsDTO permissionsDTO : permissions) {
                permissionIds.append(permissionsDTO.getId());
                permissionIds.append(",");
            }
            if (permissionIds.length() > 0) {
                permissionIds.deleteCharAt(permissionIds.length() - 1);
            }
            //获取menu
            memberDTO.setMenus(BeanUtils.copyList(menuMapper.getByPermissionIds(permissionIds.toString()), MenuDTO.class));
        }
        return BeanUtils.beanToJsonStringWithDateFormat(memberDTO);
    }

    @Override
    public PageBean list(MemberQueryRequest request) {
        PageBean<MemberDTO> page = new PageBean<>(request.getCurrentPage(),request.getPageSize());
        page.setPageData(BeanUtils.copyList(memberMapper.list(request,page), MemberDTO.class));
        return page;
    }
}
