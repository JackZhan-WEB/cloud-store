package club.jackzhan.cloudstore.module.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 16:52
 *
 * @Author: JackZhan
 */
@Data
@Accessors(chain = true)
public class RoleDTO implements Serializable{


    private List<PermissionsDTO> permissions = new ArrayList<>();

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 父级角色ID
     */
    private Integer parentId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色类型 1：可授权 0：不可授权
     */
    private Integer type;
    /**
     * 角色状态  1：正常  2：禁用
     */
    private Integer state;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 修改人
     */
    private String updateUser;
}
