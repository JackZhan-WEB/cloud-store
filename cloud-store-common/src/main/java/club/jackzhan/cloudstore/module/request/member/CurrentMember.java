package club.jackzhan.cloudstore.module.request.member;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-06-05 11:18
 *
 * @Author: JackZhan
 */
@Data
public class CurrentMember {
    /**
    * 用户标识id
    */
    private String id;

    /**
     * 用户名-登陆用的 可以初始化一次，不可以修改  可以选择手机号码登录
     */
    private String username;

}
