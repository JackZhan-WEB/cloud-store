package club.jackzhan.cloudstore.module.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * Date: 2019-04-17 16:52
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
    * 父级权限ID
    */
    private Integer parentId;

    /**
    * 权限名称
    */
    private String name;

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
