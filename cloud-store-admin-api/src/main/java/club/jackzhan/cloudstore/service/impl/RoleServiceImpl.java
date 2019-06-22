package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.service.IRoleService;
import club.jackzhan.cloudstore.util.RemoteCallUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 14:40
 *
 * @Author: JackZhan
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {


    @Autowired
    private RemoteCallUtil remoteCallUtil;

    @Override
    public ResultResponse getAllRoles() {
        return remoteCallUtil.sendGet("/member/role/getAllRoles");
    }

}
