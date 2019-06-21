package club.jackzhan.cloudstore.web.controller;

import club.jackzhan.cloudstore.service.IPermissionsService;
import club.jackzhan.cloudstore.util.ResultResponse;
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
public class PermissionsController {

    @Autowired
    private IPermissionsService permissionsService;

    @GetMapping("/list")
    @RequiresPermissions({"permissions:list", "权限列表"})
    public ResultResponse list() {
        return permissionsService.list();
    }

    @PostMapping("/loadPerms")
    @RequiresPermissions({"permissions:loadPerms", "权限加载"})
    public ResultResponse loadPerms() {
        return permissionsService.loadPerms();
    }

}
