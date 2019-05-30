package club.jackzhan.cloudstore.enums;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-01-12
 * Time: 15:41
 * @author JackZhan
 */
public enum MemberTypeEnum implements BaseEnum {
    MANAGER(1, "后台管理人员"),
    MEMBER(2, "普通用户"),
    VIP_MEMBER(3, "VIP用户");


    @Getter
    private Integer code;
    @Getter
    private String desc;

    MemberTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
