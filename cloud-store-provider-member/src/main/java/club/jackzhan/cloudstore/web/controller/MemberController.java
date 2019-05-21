package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.exception.BusinessException;
import club.jackzhan.cloudstore.module.dto.RoleDTO;
import club.jackzhan.cloudstore.module.entities.Role;
import club.jackzhan.cloudstore.module.request.MemberQueryRequest;
import club.jackzhan.cloudstore.service.IMemberService;
import club.jackzhan.cloudstore.util.CheckParametersUtil;
import club.jackzhan.cloudstore.util.PageBean;
import club.jackzhan.cloudstore.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

    @GetMapping("/getMember")
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


    @GetMapping("/list")
    public ResultResponse list(MemberQueryRequest request) {
        PageBean page;
        try {
            page = memberService.list(request);
        } catch (BusinessException e) {
            log.error("[MemberController][list] " + e.getMessage());
            return ResultResponse.failure(e.getMessage());
        } catch (Exception e) {
            log.error(e.toString());
            return ResultResponse.failure();
        }
        return ResultResponse.success(page);
    }
    @GetMapping("/getAllRoles")
    public ResultResponse getAllRoles() {
        List<RoleDTO> list;
        try {
            list = memberService.getAllRoles();
        } catch (BusinessException e) {
            log.error("[MemberController][list] " + e.getMessage());
            return ResultResponse.failure(e.getMessage());
        } catch (Exception e) {
            log.error(e.toString());
            return ResultResponse.failure();
        }
        return ResultResponse.success(list);
    }
}
