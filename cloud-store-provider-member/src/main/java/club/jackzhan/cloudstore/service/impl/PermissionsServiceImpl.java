
package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.mapper.PermissionsMapper;
import club.jackzhan.cloudstore.module.dto.PermissionsDTO;
import club.jackzhan.cloudstore.entities.Permissions;
import club.jackzhan.cloudstore.module.request.PermissionsRequest;
import club.jackzhan.cloudstore.service.IPermissionsService;
import club.jackzhan.cloudstore.util.BeanUtils;
import club.jackzhan.cloudstore.util.CheckParametersUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 18:01
 *
 * @Author: JackZhan
 */
@Service
public class PermissionsServiceImpl implements IPermissionsService {

    @Autowired
    private PermissionsMapper permissionsMapper;

    @Override
    public List<PermissionsDTO> list() {
        List<Permissions> list = permissionsMapper.list();
        return BeanUtils.copyList(list,PermissionsDTO.class);
    }

    @Override
    public boolean saveList(PermissionsRequest request) {
        if (CheckParametersUtil.getInstance().isEmpty(request.getList())) {
            return Boolean.TRUE;
        }
        List<Permissions> list = BeanUtils.copyList(request.getList(), Permissions.class);

        return permissionsMapper.insertList(list) > 0;
    }
}
