package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.module.dto.MemberDTO;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
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


    @Autowired
    private RemoteCallUtil remoteCallUtil;

    @Override
    public ResultResponse createUser(MemberQueryRequest request) {
        //加密次数
        int hashIterations = 1;
        String salt = RandomUtil.generateStr(6);
        request.setSalt(salt).setPassword(new SimpleHash("MD5",request.getPassword(),salt,hashIterations).toString());
        return remoteCallUtil.sendPost("/member/createUser",request);
    }
}
