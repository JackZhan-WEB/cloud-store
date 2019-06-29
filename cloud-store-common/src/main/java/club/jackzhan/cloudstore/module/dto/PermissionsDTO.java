package club.jackzhan.cloudstore.module.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 16:52
 *
 * @Author: JackZhan
 */
@Data
@Accessors(chain = true)
public class PermissionsDTO implements Serializable {
    /**
     * 权限id
     */
    private Integer id;

    /**
     * 父级权限Code
     */
    private String parentCode;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限code
     */
    private String code;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限类型 1：可授权 0：不可授权
     */
    private Integer type;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
