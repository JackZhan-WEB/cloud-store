package club.jackzhan.cloudstore.module.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 15:49
 *
 * @Author: JackZhan
 */
public class PageQueryRequest extends BaseRequest{
    private Integer currentPage = 1;
    private Integer pageSize = 20;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
