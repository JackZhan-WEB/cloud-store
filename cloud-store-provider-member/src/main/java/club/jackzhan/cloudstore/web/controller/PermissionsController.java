package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.PermissionsRequest;
import club.jackzhan.cloudstore.service.IPermissionsService;
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
@RequestMapping("/permissions")
@Slf4j
@Api(value = "Permissions", tags = {"Permissions"}, description = "权限相关")
public class PermissionsController {

    @Resource
    private IPermissionsService permissionsService;


    /**
     * 查询所有的权限列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "权限列表")
    public ResultResponse list() {
        return ResultResponse.success(permissionsService.list());
    }

    /**
     * 查询所有的权限列表
     */
    @GetMapping("/getAllCodes")
    @ApiOperation(value = "获取所有的权限code")
    public ResultResponse getAllCodes() {
        return ResultResponse.success(permissionsService.getAllCodes());
    }

    /**
     * 保存权限列表
     */
    @PostMapping("/saveList")
    @ApiOperation(value = "权限加载")
    public ResultResponse saveList(@RequestBody PermissionsRequest request) {
        return ResultResponse.success(permissionsService.saveList(request));
    }


}
