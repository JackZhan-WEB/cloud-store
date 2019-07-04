package club.jackzhan.cloudstore.entities;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * Date: 2019-04-17 16:52
 * @Author: JackZhan
 */
@Data
@Accessors(chain = true)
public class Role implements Serializable {
    /**
    * 角色id
    */
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;

    /**
    * 父级角色ID
    */
    private Integer parentId;

    /**
    * 角色编码
    */
    private String code;

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
    * 角色状态  1：正常  2：禁用
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
    * 修改人
    */
    private String updateUser;

    private static final long serialVersionUID = 1L;
}
