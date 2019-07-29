package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.common.BaseIdRequest;
import club.jackzhan.cloudstore.module.request.common.BaseIdsRequest;
import club.jackzhan.cloudstore.module.request.common.PageQueryRequest;
import club.jackzhan.cloudstore.module.request.member.CurrentMember;
import club.jackzhan.cloudstore.module.request.member.MemberUpdateRequest;
import club.jackzhan.cloudstore.module.request.role.RoleCreateRequest;
import club.jackzhan.cloudstore.module.request.role.RoleRemoveRequest;
import club.jackzhan.cloudstore.module.request.role.RoleUpdateRequest;
import club.jackzhan.cloudstore.service.IRoleService;
import club.jackzhan.cloudstore.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-16 16:27
 *
 * @Author: JackZhan
 */
@RestController
@RequestMapping("/role")
@RequiresPermissions({"role:*","角色模块"})
@Api(value = "Role", tags = {"Role"}, description = "角色相关")
public class RoleController {

    @Autowired
    private IRoleService roleService;


    @GetMapping("/getAllRoles")
    @ApiOperation(value = "所有角色")
    @RequiresPermissions({"role:list", "所有角色"})
    public ResultResponse getAllRoles(){
        return roleService.getAllRoles();
    }


    @GetMapping("/list")
    @RequiresPermissions({"role:list","角色列表"})
    @ApiOperation(value = "角色列表")
    public ResultResponse list(PageQueryRequest request){
        return roleService.list(request);
    }

    @PostMapping("/createRole")
    @RequiresPermissions({"role:createRole","创建角色"})
    @ApiOperation(value = "创建角色")
    public ResultResponse createRole(@RequestBody RoleCreateRequest request, CurrentMember currentMember){
        return roleService.createRole(request,currentMember);
    }

    @DeleteMapping("/deleteRole/{id}")
    @RequiresPermissions({"role:deleteRole","删除角色"})
    @ApiOperation(value = "删除角色")
    public ResultResponse deleteRole(@RequestBody @PathVariable Integer id, CurrentMember currentMember){
        return roleService.deleteRole(id,currentMember);
    }

    @PostMapping("/batchDelete")
    @RequiresPermissions({"role:batchDelete", "批量删除"})
    @ApiOperation(value = "批量删除")
    public ResultResponse batchDelete(@RequestBody @Valid BaseIdsRequest<Integer> request, CurrentMember currentMember) {
        return roleService.batchDelete(request,currentMember);
    }

    @PostMapping("/updateRole")
    @RequiresPermissions({"role:updateRole", "修改角色"})
    @ApiOperation(value = "修改角色")
    public ResultResponse updateRole(@RequestBody RoleUpdateRequest request, CurrentMember currentMember) {
        return roleService.updateRole(request,currentMember);
    }

}
