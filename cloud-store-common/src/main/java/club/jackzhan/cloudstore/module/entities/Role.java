package club.jackzhan.cloudstore.module.entities;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * Date: 2019-04-17 16:52
 * @Author: JackZhan
 */
@Data
public class Role implements Serializable {
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
    private Integer type;

    /**
    * 角色状态  1：正常  2：禁用  3：已删除
    */
    private Integer state;

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
