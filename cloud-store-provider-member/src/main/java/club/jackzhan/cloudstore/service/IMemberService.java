package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.dto.member.MemberDTO;
import club.jackzhan.cloudstore.module.request.member.MemberCreateRequest;
import club.jackzhan.cloudstore.module.request.member.MemberQueryRequest;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 18:00
 *
 * @Author: JackZhan
 */
public interface IMemberService {

    /**
     * 根据用户的登陆名获取用户信息
     * @param request
     * @return
     */
    String getMember(MemberQueryRequest request);

    Page<MemberDTO> list(MemberQueryRequest request);

    Boolean createUser(MemberCreateRequest request);

    Boolean updateUser(MemberQueryRequest request);

    Boolean verifyUsername(MemberQueryRequest request);

    Boolean verifyPhone(MemberQueryRequest request);
}
