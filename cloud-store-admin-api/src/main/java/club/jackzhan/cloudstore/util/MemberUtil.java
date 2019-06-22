package club.jackzhan.cloudstore.util;

import club.jackzhan.cloudstore.enums.ErrorCodeEnum;
import club.jackzhan.cloudstore.exception.BusinessException;
import club.jackzhan.cloudstore.module.request.member.CurrentMember;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-28 18:04
 *
 * @Author: JackZhan
 */
@Component
public class MemberUtil {

    private static RedisOperation redisOperation;

    @Autowired
    public MemberUtil(RedisOperation redisOperation){
        MemberUtil.redisOperation = redisOperation;
    }

    private MemberUtil(){}
    public static CurrentMember getCurrentMember() {
        Object obj = redisOperation.get(getSessionId().toString());
        if(obj == null){
            throw new BusinessException(ErrorCodeEnum.MEMBER_SESSION_TIME_OUT);
        }
        CurrentMember currentMember = BeanUtils.json2Bean(obj.toString(), CurrentMember.class);
        if(currentMember == null){
            throw new BusinessException(ErrorCodeEnum.MEMBER_SESSION_TIME_OUT);
        }

        return currentMember;
    }

    public static Serializable getSessionId() {
        return SecurityUtils.getSubject().getSession().getId();
    }
}
