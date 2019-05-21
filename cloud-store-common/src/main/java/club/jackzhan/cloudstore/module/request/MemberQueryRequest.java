package club.jackzhan.cloudstore.module.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

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
public class MemberQueryRequest extends PageQueryRequest {

    private String loginName;

    private String memberId;

    private String password;

    private String username;

    private String pbone;

}
