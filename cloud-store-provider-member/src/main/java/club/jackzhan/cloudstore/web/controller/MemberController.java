package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.member.CurrentMember;
import club.jackzhan.cloudstore.module.request.member.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.CheckParametersUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Member", tags = {"Member"}, description = "用户相关")
public class MemberController {

    @Resource
    private IMemberService memberService;

    /**
     * 获取用户的信息
     */
    @GetMapping("/getMember")
    @ApiOperation(value = "获取用户的信息")
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
    @ApiOperation(value = "用户列表")
    public ResultResponse list(MemberQueryRequest request) {
        return ResultResponse.success(memberService.list(request));
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/updateUser")
    @ApiOperation(value = "用户更新")
    public ResultResponse updateUser(@RequestBody MemberQueryRequest request, CurrentMember member) {
        return ResultResponse.success(memberService.updateUser(request));
    }

    /**
     * 创建用户
     */
    @PostMapping("/createUser")
    @ApiOperation(value = "用户添加")
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
