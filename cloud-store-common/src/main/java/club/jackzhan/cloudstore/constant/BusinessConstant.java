package club.jackzhan.cloudstore.constant;

import club.jackzhan.cloudstore.exception.BusinessException;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-24 9:14
 *
 * @Author: JackZhan
 */
public interface BusinessConstant {

    /**
     * 密码盐的长度
     */
    Integer PASSWORD_SALT_LENGTH = 6;
    /**
     * 密码加密次数
     */
    Integer PASSWORD_ENCRYPTION_TIMES = 1;

    /**
     * 密码加密类型
     */
    String PASSWORD_ENCRYPTION_TYPE = "md5";

    /**
     * 异常分隔符
     */
    String BUSINESS_EXCEPTION_SPLIT = ",code:";

    /**
     * token过期时间 秒
     */
    long TOKEN_EXPIRE_TIME = 300;
    /**
     * token 参数名
     */
    String TOKEN = "token";

}
