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

    @Autowired
    private RemoteCallUtil remoteCallUtil;

    @GetMapping("/list")
    public ResultResponse list(MemberQueryRequest request){
        return remoteCallUtil.sendGet("/member/list", request);
    }

    @GetMapping("/getAllRoles")
    public ResultResponse getAllRoles(MemberQueryRequest request){
        return remoteCallUtil.sendGet("/member/getAllRoles");
    }

    @PostMapping("/createUser")
    public ResultResponse createUser(MemberQueryRequest request){
        return memberService.createUser(request);
    }

}
