package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.module.request.common.PageQueryRequest;
import club.jackzhan.cloudstore.module.request.member.CurrentMember;
import club.jackzhan.cloudstore.module.request.role.RoleCreateRequest;
import club.jackzhan.cloudstore.module.request.role.RoleRemoveRequest;
import club.jackzhan.cloudstore.service.IRoleService;
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

    @PostMapping("/removeRole")
    @RequiresPermissions({"role:removeRole","删除角色"})
    @ApiOperation(value = "删除角色")
    public ResultResponse removeRole(@RequestBody RoleRemoveRequest request, CurrentMember currentMember){
        return roleService.removeRole(request,currentMember);
    }


}
