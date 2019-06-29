package club.jackzhan.cloudstore.module.request.common;

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
public class BaseRequest{
    private String token;

    private String updateUser;
}
