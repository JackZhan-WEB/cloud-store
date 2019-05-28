package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.enums.BusinessConstant;
import club.jackzhan.cloudstore.enums.MemberTypeEnum;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.RandomUtil;
import club.jackzhan.cloudstore.util.RemoteCallUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 14:40
 *
 * @Author: JackZhan
 */
@Service("memberService")
public class MemberServiceImpl implements IMemberService {


    @Autowired
    private RemoteCallUtil remoteCallUtil;

    @Override
    public ResultResponse createUser(@RequestBody MemberQueryRequest request) {
        request.setType(MemberTypeEnum.MANAGER.getCode());
        //加密次数
        int hashIterations = BusinessConstant.PASSWORD_ENCRYPTION_TIMES;
        String salt = RandomUtil.generateStr(BusinessConstant.PASSWORD_SALT_LENGTH);
        request.setSalt(salt).setPassword(new SimpleHash(BusinessConstant.PASSWORD_ENCRYPTION_TYPE,request.getPassword(),salt,hashIterations).toString());
        return remoteCallUtil.sendPost("/member/createUser",request);
    }
}
