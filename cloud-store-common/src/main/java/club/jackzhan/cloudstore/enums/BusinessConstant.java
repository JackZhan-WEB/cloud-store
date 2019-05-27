package club.jackzhan.cloudstore.enums;

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



}
