package club.jackzhan.cloudstore.module.request.member;

import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.module.request.common.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: @EqualsAndHashCode  不会调用父类中的hashCode等方法
 * Date: 2019-04-17 18:07
 *
 * @Author: JackZhan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class MemberCreateRequest extends BaseRequest {

    @ApiModelProperty(required = true, value = "昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty(required = true, value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(hidden = true, value = "盐")
    private String salt;

    @ApiModelProperty(required = true, value = "用户类型")
    private Integer type;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(required = true, value = "用户名")
    private String username;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号格式不正确")
    @ApiModelProperty(required = true, value = "手机号")
    private String phone;

    @NotEmpty(message = "用户的角色不能为空")
    @ApiModelProperty(required = true, value = "用户的角色")
    private List<RoleDTO> roles;
}
