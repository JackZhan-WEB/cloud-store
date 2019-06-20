package club.jackzhan.cloudstore.entities;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * Date: 2019-04-17 16:52
 * @Author: JackZhan
 */
@Data
public class Permissions {
    /**
    * 权限id
    */
    @TableId(value="id", type= IdType.AUTO)
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
    private Date createTime;

}
