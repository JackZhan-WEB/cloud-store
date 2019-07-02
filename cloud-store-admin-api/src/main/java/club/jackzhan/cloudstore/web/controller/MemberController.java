package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.member.CurrentMember;
import club.jackzhan.cloudstore.module.request.member.MemberCreateRequest;
import club.jackzhan.cloudstore.module.request.member.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-16 16:27
 *
 * @Author: JackZhan
 */
@RestController
@RequestMapping("/member")
@RequiresPermissions({"member:*", "用户模块"})
@Api(value = "Member", tags = {"Member"}, description = "用户相关")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @GetMapping("/list")
    @RequiresPermissions({"member:list", "用户列表"})
    @ApiOperation(value = "用户列表")
    public ResultResponse list(MemberQueryRequest request) {
        return memberService.list(request);
    }

    @GetMapping("/verifyUsername")
    @ApiOperation(value = "验证用户名重复")
    public ResultResponse verifyUsername(MemberQueryRequest request) {
        return memberService.verifyUsername(request);
    }

    @GetMapping("/verifyPhone")
    @ApiOperation(value = "验证手机号码重复")
    public ResultResponse verifyPhone(MemberQueryRequest request) {
        return memberService.verifyPhone(request);
    }

    @PostMapping("/createUser")
    @RequiresPermissions({"member:createUser", "用户添加"})
    @ApiOperation(value = "用户添加")
    public ResultResponse createUser(@RequestBody @Valid MemberCreateRequest request, CurrentMember currentMember) {
        return memberService.createUser(request, currentMember);
    }

    @PostMapping("/updateUser")
    @RequiresPermissions({"member:updateUser", "用户更新"})
    @ApiOperation(value = "用户更新")
    public ResultResponse updateUser(@RequestBody MemberQueryRequest request, CurrentMember currentMember) {
        return memberService.updateUser(request,currentMember);
    }


}
