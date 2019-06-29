package club.jackzhan.cloudstore.enums;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author : JackZhan
 * Date: 2018-12-21
 * Time: 17:14
 */
public enum ErrorCodeEnum implements BaseEnum<Integer> {
    //正常
    NORMAL(1,"操作成功"),
    //用户信息查询失败
    MEMBER_INFO_ERROR(2,"用户信息查询失败"),

    MEMBER_SESSION_TIME_OUT(3, "登陆已过期，请重新登陆"),
    //业务异常
    SERVICE_ERROR(9999,"业务异常，请稍候重试");



    @Getter
    private Integer code;
    @Getter
    private String desc;

    ErrorCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
