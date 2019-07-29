package club.jackzhan.cloudstore.module.request.member;

import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.module.request.common.PageQueryRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

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
public class MemberUpdateRequest extends PageQueryRequest {

    @ApiModelProperty(required = true, value = "id")
    @NotBlank(message = "id不能为空")
    private String id;

    private String loginName;

    private String nickname;

    private String memberId;

    private Integer type;

    private String salt;

    private String username;

    private String phone;

    private Integer state;

    private List<RoleDTO> roles;
}
