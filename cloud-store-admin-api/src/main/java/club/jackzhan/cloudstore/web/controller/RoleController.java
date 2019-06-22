package club.jackzhan.cloudstore.web.controller;

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
    @ApiOperation(value = "角色列表")
    public ResultResponse getAllRoles(){
        return roleService.getAllRoles();
    }


}
