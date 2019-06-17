package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.dto.MemberDTO;
import club.jackzhan.cloudstore.module.request.CurrentMember;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.MemberUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-16 16:27
 *
 * @Author: JackZhan
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private IMemberService memberService;

    @GetMapping("/list")
//    @RequiresPermissions("admin")
    public ResultResponse list(MemberQueryRequest request){
        return memberService.list(request);
    }

    @GetMapping("/getAllRoles")
    public ResultResponse getAllRoles(MemberQueryRequest request){
        return memberService.getAllRoles(request);
    }

    @PostMapping("/createUser")
    public ResultResponse createUser(@RequestBody MemberQueryRequest request, CurrentMember member){
        return memberService.createUser(request);
    }

    @PostMapping("/updateUser")
    public ResultResponse updateUser(@RequestBody MemberQueryRequest request, CurrentMember member){
        return memberService.updateUser(request);
    }


}
