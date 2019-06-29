package club.jackzhan.cloudstore.module.request.role;

import club.jackzhan.cloudstore.module.request.common.BaseIdRequest;
import club.jackzhan.cloudstore.module.request.common.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-06-24 16:53
 *
 * @Author: JackZhan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleRemoveRequest extends BaseIdRequest<Integer> {

    @ApiModelProperty(required = true,value = "状态")
    private Integer state;

}
