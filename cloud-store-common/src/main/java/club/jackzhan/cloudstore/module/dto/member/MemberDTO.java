package club.jackzhan.cloudstore.module.dto.member;

import club.jackzhan.cloudstore.module.dto.MenuDTO;
import club.jackzhan.cloudstore.module.dto.PermissionsDTO;
import club.jackzhan.cloudstore.module.dto.RoleDTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 16:45
 *
 * @Author: JackZhan
 */
@Data
@Accessors(chain = true)
public class MemberDTO implements Serializable {

    private List<RoleDTO> roles = new ArrayList<>();
    private List<PermissionsDTO> permissions = new ArrayList<>();
    private List<MenuDTO> menus = new ArrayList<>();
    /**
     * 用户标识id
     */
    private String id;

    /**
     * 用户名-登陆用的 可以初始化一次，不可以修改  可以选择手机号码登录
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 密码输入错误次数
     */
    private Integer pwdErrCount;

    /**
     * 昵称-用户显示给其他用户看的，可以中文，可以修改
     */
    private String nickname;

    /**
     * 手机
     */
    private String phone;

    /**
     * 手机验证码
     */
    private String phoneCode;

    /**
     * 手机验证码发送时间
     */
    private Date phoneCodeTime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态 1：正常 2：密码错误次数过多被禁用 3：管理员禁用 4：注销
     */
    private Integer state;

    /**
     * 用户类型 1：管理员 2：普通用户 3：VIP用户
     */
    private Integer type;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改时间
     */
    private String updateUser;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 登录次数
     */
    private Integer loginCount;


}
