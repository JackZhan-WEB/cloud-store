package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.PermissionsRequest;
import club.jackzhan.cloudstore.service.IPermissionsService;
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
@RequestMapping("/permissions")
@Slf4j
public class PermissionsController {

    @Resource
    private IPermissionsService permissionsService;


    @GetMapping("/list")
    public ResultResponse list() {
        return ResultResponse.success(permissionsService.list());
    }

    @PostMapping("/saveList")
    public ResultResponse saveList(@RequestBody PermissionsRequest request) {
        return ResultResponse.success(permissionsService.saveList(request));
    }


}
