package club.jackzhan.cloudstore.module.request;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 18:07
 *
 * @Author: JackZhan
 */
@Data
public class MemberQueryRequest {

    private String loginName;

    private String memberId;

    private String password;

}
