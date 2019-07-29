package club.jackzhan.cloudstore.module.request.common;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 15:49
 *
 * @Author: JackZhan
 */
public class BaseIdsRequest<T> extends BaseRequest {

    @ApiModelProperty(value = "ids")
    private List<T> ids;

    public List<T> getIds() {
        return ids;
    }

    public void setIds(List<T> ids) {
        this.ids = ids;
    }
}
