package club.jackzhan.cloudstore.util;

import club.jackzhan.cloudstore.constant.Constants;
import club.jackzhan.cloudstore.enums.ErrorCodeEnum;
import club.jackzhan.cloudstore.exception.BusinessException;
import club.jackzhan.cloudstore.module.dto.MemberDTO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-28 18:04
 *
 * @Author: JackZhan
 */
public class MemberUtil {

    @Autowired
    private RedisOperation redisOperation;

    private MemberUtil(){}
    public static MemberDTO getCurrentMember() {
//        BeanUtils.json2Bean(redisOperation.get(token),MemberDTO.class);
        MemberDTO currentMember = (MemberDTO) SecurityUtils.getSubject().getSession().getAttribute(Constants.MEMBER_IN_SESSION);
        if(currentMember == null){
            throw new BusinessException(ErrorCodeEnum.MEMBER_SESSION_TIME_OUT);
        }

        return currentMember;
    }

    public static Serializable getSessionId() {
        return SecurityUtils.getSubject().getSession().getId();
    }
}
