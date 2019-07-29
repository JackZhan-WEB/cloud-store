package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.request.member.CurrentMember;
import club.jackzhan.cloudstore.module.request.member.MemberCreateRequest;
import club.jackzhan.cloudstore.module.request.member.MemberQueryRequest;
import club.jackzhan.cloudstore.module.request.member.MemberUpdateRequest;
import club.jackzhan.cloudstore.util.ResultResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 14:39
 *
 * @Author: JackZhan
 */
public interface IMemberService {
    ResultResponse createUser(MemberCreateRequest request, CurrentMember currentMember);

    ResultResponse list(MemberQueryRequest request);

    ResultResponse getAllRoles();

    ResultResponse updateUser(MemberUpdateRequest request, CurrentMember currentMember);

    ResultResponse verifyUsername(MemberQueryRequest request);

    ResultResponse verifyPhone(MemberQueryRequest request);
}
