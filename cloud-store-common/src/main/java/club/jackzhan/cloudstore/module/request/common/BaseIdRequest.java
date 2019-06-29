package club.jackzhan.cloudstore.module.request.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 15:49
 *
 * @Author: JackZhan
 */
public class BaseIdRequest<T> extends BaseRequest {

    @ApiModelProperty(value = "ID")
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
