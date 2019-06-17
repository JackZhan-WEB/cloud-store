package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.dto.MemberDTO;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.util.ResultResponse;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 14:39
 *
 * @Author: JackZhan
 */
public interface IMemberService {
    ResultResponse createUser(MemberQueryRequest request);

    ResultResponse list(MemberQueryRequest request);

    ResultResponse getAllRoles(MemberQueryRequest request);

    ResultResponse updateUser(MemberQueryRequest request);
}
