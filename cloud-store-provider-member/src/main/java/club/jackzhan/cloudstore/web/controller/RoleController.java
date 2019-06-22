package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.service.IRoleService;
import club.jackzhan.cloudstore.util.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @ApiOperation(value = "角色列表")
    public ResultResponse getAllRoles() {
        return ResultResponse.success(roleService.getAllRoles());
    }
}
