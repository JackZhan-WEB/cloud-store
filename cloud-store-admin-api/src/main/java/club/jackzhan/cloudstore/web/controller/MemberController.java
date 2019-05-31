package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.RemoteCallUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
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
    public ResultResponse list(MemberQueryRequest request){


        return memberService.list(request);
    }

    @GetMapping("/getAllRoles")
    public ResultResponse getAllRoles(MemberQueryRequest request){
        return RemoteCallUtil.create.sendGet("/member/getAllRoles");
    }

    @PostMapping("/createUser")
    public ResultResponse createUser(@RequestBody MemberQueryRequest request){
        return memberService.createUser(request);
    }

}
