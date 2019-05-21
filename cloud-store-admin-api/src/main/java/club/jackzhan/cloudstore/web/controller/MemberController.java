package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.util.RemoteCallUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private RemoteCallUtil remoteCallUtil;

    @GetMapping("/list")
    public ResultResponse list(MemberQueryRequest request){
        return remoteCallUtil.sendGet("/member/list", request);
    }

    @GetMapping("/getAllRoles")
    public ResultResponse getAllRoles(){
        return remoteCallUtil.sendGet("/member/getAllRoles");
    }

}
