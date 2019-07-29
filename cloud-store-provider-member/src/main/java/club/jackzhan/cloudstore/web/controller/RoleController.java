package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.common.BaseIdRequest;
import club.jackzhan.cloudstore.module.request.common.BaseIdsRequest;
import club.jackzhan.cloudstore.module.request.common.PageQueryRequest;
import club.jackzhan.cloudstore.module.request.role.RoleCreateRequest;
import club.jackzhan.cloudstore.module.request.role.RoleRemoveRequest;
import club.jackzhan.cloudstore.module.request.role.RoleUpdateRequest;
import club.jackzhan.cloudstore.service.IRoleService;
import club.jackzhan.cloudstore.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
        if (roleService.createRole(request)) {
            return ResultResponse.success();
        }else {
            return ResultResponse.failure("创建失败！");
        }

    }

    @PostMapping("/deleteRole")
    @ApiOperation(value = "删除角色")
    public ResultResponse deleteRole(@RequestBody BaseIdRequest<Integer> request){
        if (roleService.deleteRole(request)) {
            return ResultResponse.success();
        }else {
            return ResultResponse.failure("删除失败！");
        }
    }

    @PostMapping("/batchDelete")
    @ApiOperation(value = "批量删除")
    public ResultResponse batchDelete(@RequestBody @Valid BaseIdsRequest<Integer> request){
        if (roleService.batchDelete(request)) {
            return ResultResponse.success();
        }else {
            return ResultResponse.failure("删除失败！");
        }
    }

    @PostMapping("/updateRole")
    @ApiOperation(value = "修改角色")
    public ResultResponse updateRole(@RequestBody RoleUpdateRequest request){
        if (roleService.updateRole(request)) {
            return ResultResponse.success();
        }else {
            return ResultResponse.failure("修改失败！");
        }
    }
}
