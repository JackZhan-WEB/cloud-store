package club.jackzhan.cloudstore.module.request;

import club.jackzhan.cloudstore.module.dto.PermissionsDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-06-19 18:28
 *
 * @Author: JackZhan
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class PermissionsRequest extends BaseRequest{

    private List<PermissionsDTO> list;
}
