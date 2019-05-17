package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.util.ResultResponse;
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

    @GetMapping("/list")
    public ResultResponse list(){
        return null;
    }

}
