package club.jackzhan.cloudstore.util;

import club.jackzhan.cloudstore.constant.Constants;
import club.jackzhan.cloudstore.enums.ErrorCodeEnum;
import club.jackzhan.cloudstore.exception.BusinessException;
import club.jackzhan.cloudstore.module.dto.MemberDTO;
import org.apache.shiro.SecurityUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-28 18:04
 *
 * @Author: JackZhan
 */
public class MemberUtil {

    private MemberUtil(){}
    public static MemberDTO getCurrentMember() {
        MemberDTO currentMember = (MemberDTO) SecurityUtils.getSubject().getSession().getAttribute(Constants.MEMBER_IN_SESSION);
        if(currentMember == null){
            throw new BusinessException(ErrorCodeEnum.MEMBER_SESSION_TIME_OUT);
        }

        return currentMember;
    }
}
