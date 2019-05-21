package club.jackzhan.cloudstore.module.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 15:49
 *
 * @Author: JackZhan
 */
@Getter
@Setter
public class PageQueryRequest extends BaseRequest{
    private Integer currentPage = 1;
    private Integer pageSize = 20;

}
