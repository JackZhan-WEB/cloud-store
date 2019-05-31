package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.constant.BusinessConstant;
import club.jackzhan.cloudstore.enums.MemberTypeEnum;
import club.jackzhan.cloudstore.module.dto.MemberDTO;
import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.MemberUtil;
import club.jackzhan.cloudstore.util.RandomUtil;
import club.jackzhan.cloudstore.util.RemoteCallUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 14:40
 *
 * @Author: JackZhan
 */
@Service("memberService")
public class MemberServiceImpl implements IMemberService {



    @Override
    public ResultResponse createUser(MemberQueryRequest request) {
        MemberDTO currentMember = MemberUtil.getCurrentMember();
        request.setUpdateUser(currentMember.getNickname());
        request.setType(MemberTypeEnum.MANAGER.getCode());
        //加密次数
        int hashIterations = BusinessConstant.PASSWORD_ENCRYPTION_TIMES;
        String salt = RandomUtil.generateStr(BusinessConstant.PASSWORD_SALT_LENGTH);
        request.setSalt(salt).setPassword(new SimpleHash(BusinessConstant.PASSWORD_ENCRYPTION_TYPE, request.getPassword(), salt, hashIterations).toString());
        List<RoleDTO> roles = request.getRoles();
        for (RoleDTO role : roles) {
            if ("admin".equals(role.getName())) {
                roles.clear();
                roles.add(role);
                break;
            }
        }
        return RemoteCallUtil.create.sendPost("/member/createUser", request);
    }

    @Override
    public ResultResponse list(MemberQueryRequest request) {
        return RemoteCallUtil.create.sendGet("/member/list", request);
    }
}
