package club.jackzhan.cloudstore.cfgbeans;

import club.jackzhan.cloudstore.constant.BusinessConstant;
import club.jackzhan.cloudstore.module.dto.MemberDTO;
import club.jackzhan.cloudstore.module.dto.PermissionsDTO;
import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.module.request.member.MemberQueryRequest;
import club.jackzhan.cloudstore.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-19 15:44
 *
 * @Author: JackZhan
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private RemoteCallUtil remoteCallUtil;

    @Autowired
    private RedisOperation redisOperation;

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        ResultResponse memberResponse = remoteCallUtil.sendGet("member.member.getMember", new MemberQueryRequest().setLoginName(token.getUsername()));
        MemberDTO memberDTO = null;
        try {
            memberDTO = BeanUtils.json2Bean((String) memberResponse.getData(), MemberDTO.class);
        } catch (Exception e) {
            log.error(e.toString());
        }
        if (memberDTO == null || memberDTO.getId() == null) {
            return null;
        }
        log.info("==开始认证==");
        System.out.println(MemberUtil.getSessionId());

        //将用户信息放入redis中
        redisOperation.set(MemberUtil.getSessionId().toString(), BeanUtils.bean2Json(memberDTO), BusinessConstant.TOKEN_EXPIRE_TIME);
        //将用户信息放入session中
//        SecurityUtils.getSubject().getSession().setAttribute(Constants.MEMBER_IN_SESSION, memberDTO);
        return new SimpleAuthenticationInfo(memberDTO, memberDTO.getPassword().toCharArray(), ByteSource.Util.bytes(memberDTO.getSalt()), getName());
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        MemberDTO memberDTO = (MemberDTO) principals.getPrimaryPrincipal();

        //取出session中的用户信息
//        memberDTO = (MemberDTO) SecurityUtils.getSubject().getSession().getAttribute(Constants.MEMBER_IN_SESSION);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 将用户对应的角色和权限信息打包放到AuthorizationInfo中
        List<RoleDTO> roles = memberDTO.getRoles();
        for (RoleDTO role : roles) {
            if ("admin".equals(role.getCode())) {
                info.addStringPermission("*:*");
                info.addRole("admin");
                return info;
            } else {
                info.addRole(role.getCode());
                for (PermissionsDTO p : role.getPermissions()) {
                    info.addStringPermission(p.getName());
                }
            }
        }
        log.info("==开始授权==");
        return info;
    }

}
