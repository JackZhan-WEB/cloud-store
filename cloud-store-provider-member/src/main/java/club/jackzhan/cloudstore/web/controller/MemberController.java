package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.CurrentMember;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.CheckParametersUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 17:49
 *
 * @Author: JackZhan
 */
@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @Resource
    private IMemberService memberService;

    /**
     * 获取用户的信息
     */
    @GetMapping("/getMember")
    public ResultResponse getMember(MemberQueryRequest request) {
        CheckParametersUtil
                .getInstance()
                .put(request.getLoginName(), "loginName")
                .checkParameter();
        return ResultResponse.success(memberService.getMember(request));
    }


    /**
     * 获取用户列表
     */
    @GetMapping("/list")
    public ResultResponse list(MemberQueryRequest request) {
        return ResultResponse.success(memberService.list(request));
    }

    /**
     * 获取所有的角色
     */
    @GetMapping("/getAllRoles")
    public ResultResponse getAllRoles() {
        return ResultResponse.success(memberService.getAllRoles());
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/updateUser")
    public ResultResponse updateUser(@RequestBody MemberQueryRequest request, CurrentMember member) {
        return ResultResponse.success(memberService.updateUser(request));
    }

    /**
     * 创建用户
     */
    @PostMapping("/createUser")
    public ResultResponse createUser(@RequestBody MemberQueryRequest request) {
        CheckParametersUtil
                .getInstance()
                .put(request.getUsername(), "用户名")
                .put(request.getSalt(), "salt")
                .put(request.getPassword(), "密码")
                .put(request.getNickname(), "昵称")
                .checkParameter();
        return ResultResponse.success(memberService.createUser(request));
    }
}
