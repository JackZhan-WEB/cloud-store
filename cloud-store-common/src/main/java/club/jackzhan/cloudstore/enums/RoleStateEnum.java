package club.jackzhan.cloudstore.enums;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * @author JackZhan
 * Date: 2019-01-12
 * Time: 15:41
 */
public enum RoleStateEnum implements BaseEnum {
    NORMAL(1, "正常"),
    DISABLE(2, "禁用"),
    DELETE(3, "已删除");


    @Getter
    private Integer code;
    @Getter
    private String desc;

    RoleStateEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
