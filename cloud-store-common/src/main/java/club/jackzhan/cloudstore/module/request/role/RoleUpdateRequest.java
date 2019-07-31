package club.jackzhan.cloudstore.module.request.role;

import club.jackzhan.cloudstore.module.request.common.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-06-24 16:53
 *
 * @Author: JackZhan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleUpdateRequest extends BaseRequest {

    @NotNull(message = "id不能为空！")
    private Integer id;

    @ApiModelProperty(required = true,value = "角色名")
    private String name;

    @ApiModelProperty(required = true,value = "描述")
    private String description;

    @ApiModelProperty(required = true,value = "角色编码")
    private String code;

    @ApiModelProperty(required = true,value = "权限")
    private Integer[] permChecks;

    @ApiModelProperty(required = true,value = "角色")
    private Integer[] roleChecks;

}
