package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.common.PageQueryRequest;
import club.jackzhan.cloudstore.module.request.role.RoleCreateRequest;
import club.jackzhan.cloudstore.module.request.role.RoleRemoveRequest;
import club.jackzhan.cloudstore.service.IRoleService;
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
@RequestMapping("/role")
@Slf4j
@Api(value = "Role", tags = {"Role"}, description = "角色相关")
public class RoleController {

    @Resource
    private IRoleService roleService;

    /**
     * 获取所有的角色
     */
    @GetMapping("/getAllRoles")
    @ApiOperation(value = "获取所有的角色")
    public ResultResponse getAllRoles() {
        return ResultResponse.success(roleService.getAllRoles());
    }

    /**
     * 获取角色列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "角色列表")
    public ResultResponse list(PageQueryRequest request) {
        return ResultResponse.success(roleService.list(request));
    }


    @PostMapping("/createRole")
    @ApiOperation(value = "创建角色")
    public ResultResponse createRole(@RequestBody RoleCreateRequest request){
        return ResultResponse.success(roleService.createRole(request));
    }

    @PostMapping("/removeRole")
    @ApiOperation(value = "删除角色")
    public ResultResponse removeRole(@RequestBody RoleRemoveRequest request){
        return ResultResponse.success(roleService.removeRole(request));
    }
}
