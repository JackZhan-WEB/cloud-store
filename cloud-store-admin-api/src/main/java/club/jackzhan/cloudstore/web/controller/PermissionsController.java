package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.common.BaseIdRequest;
import club.jackzhan.cloudstore.service.IPermissionsService;
import club.jackzhan.cloudstore.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("/permissions")
@RequiresPermissions({"permissions:*", "权限模块"})
@Api(value = "Permissions", tags = {"Permissions"}, description = "权限相关")
public class PermissionsController {

    @Autowired
    private IPermissionsService permissionsService;

    @GetMapping("/list")
    @RequiresPermissions({"permissions:list", "权限列表"})
    @ApiOperation(value = "权限列表")
    public ResultResponse list() {
        return permissionsService.list();
    }

    @GetMapping("/getPerms")
    @ApiOperation(value = "可授权权限列表")
    public ResultResponse getPerms() {
        return permissionsService.getPerms();
    }

    @GetMapping("/getCheckPerms")
    @ApiOperation(value = "获取当前用户的权限")
    public ResultResponse getCheckPerms(BaseIdRequest<Integer> request) {
        return permissionsService.getCheckPerms(request);
    }

    @PostMapping("/loadPerms")
    @RequiresPermissions({"permissions:loadPerms", "权限加载"})
    @ApiOperation(value = "权限加载")
    public ResultResponse loadPerms() {
        return permissionsService.loadPerms();
    }

}
