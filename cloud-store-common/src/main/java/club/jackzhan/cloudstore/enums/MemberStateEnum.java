package club.jackzhan.cloudstore.enums;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author JackZhan
 * Date: 2019-01-12
 * Time: 15:41
 */
public enum MemberStateEnum implements BaseEnum {
    NORMAL(0, "正常"),
    DISABLE(1, "禁用"),
    DELETE(9999, "注销");


    @Getter
    private Integer code;
    @Getter
    private String desc;

    MemberStateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
