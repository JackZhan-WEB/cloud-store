package club.jackzhan.cloudstore.module.dto;

import club.jackzhan.cloudstore.module.entities.Permissions;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * Date: 2019-04-17 16:52
 * @Author: JackZhan
 */
@Data
@Accessors(chain = true)
public class RoleDTO implements Serializable {


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
    * 角色描述
    */
    private String description;

    /**
    * 角色类型 1：可授权 0：不可授权
    */
    private Boolean type;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 修改时间
    */
    private Date updateTime;

    /**
    * 创建人
    */
    private String createUser;

    /**
    * 修改人
    */
    private String updateUser;

    private static final long serialVersionUID = 1L;
}
