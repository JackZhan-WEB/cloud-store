package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.exception.BusinessException;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.CheckParametersUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 17:49
 *
 * @Author: JackZhan
 */
@RestController
@Slf4j
public class MemberController {

    @Resource
    private IMemberService memberService;

    @GetMapping("/member/getMember")
    public ResultResponse getMember(MemberQueryRequest request) {
        String json;
        try {
            CheckParametersUtil
                    .getInstance()
                    .put(request.getLoginName(), "loginName")
                    .checkParameter();
            json = memberService.getMember(request);
        } catch (BusinessException e) {
            log.error("[MemberController][getMember] " + e.getMessage());
            return ResultResponse.failure(e.getMessage());
        } catch (Exception e) {
            log.error(e.toString());
            return ResultResponse.failure();
        }
        return ResultResponse.success(json);
    }

}