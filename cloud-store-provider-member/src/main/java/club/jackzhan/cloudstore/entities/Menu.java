package club.jackzhan.cloudstore.entities;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-29 17:19
 *
 * @Author: JackZhan
 */
@Data
public class Menu implements Serializable {
    /**
     * 自增菜单ID
     */
    private Long id;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单标签
     */
    private String labels;

    /**
     * 是否显示：1：是  0：否
     */
    private Integer isShow;

    /**
     * 层级顺序
     */
    private Integer sortOrder;

    /**
     * 父节点ID
     */
    private Long parentId;

    /**
     * 权限code
     */
    private String permissionCode;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建日期
     */
    private Date createdAt;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateAt;

    /**
     * 权限ID
     */
    private String permissionId;

    /**
     * 菜单类型 1：菜单文件夹 2菜单文件 3按钮
     */
    private Integer type;

    private static final long serialVersionUID = 1L;
}
