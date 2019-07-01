package club.jackzhan.cloudstore.module.request.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 17:43
 *
 * @Author: JackZhan
 */
@Data
public class BaseRequest {
    @ApiModelProperty(hidden = true)
    private String token;

    @ApiModelProperty(hidden = true)
    private String updateUser;
}
