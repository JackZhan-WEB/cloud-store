
package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.enums.TrueFalseEnum;
import club.jackzhan.cloudstore.mapper.PermissionsMapper;
import club.jackzhan.cloudstore.entities.Permissions;
import club.jackzhan.cloudstore.module.dto.PermissionsTreeDTO;
import club.jackzhan.cloudstore.module.request.PermissionsRequest;
import club.jackzhan.cloudstore.service.IPermissionsService;
import club.jackzhan.cloudstore.util.BeanUtils;
import club.jackzhan.cloudstore.util.CheckParametersUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public PermissionsTreeDTO list() {

        PermissionsTreeDTO treeDTO = new PermissionsTreeDTO();
        List<PermissionsTreeDTO> treeList = new ArrayList<>();
        treeDTO.setChildren(treeList);
        //查询出所有的可以授权的权限
        treeDTO.setCheckList(permissionsMapper.getCheckList(TrueFalseEnum.TRUE.getCode()));

        //查询所有的权限父级
        List<Permissions> parentList = permissionsMapper.selectList(new EntityWrapper<Permissions>().isNull("parent_code"));
        for (Permissions parent : parentList) {
            PermissionsTreeDTO parentTreeDTO = new PermissionsTreeDTO();
            treeList.add(parentTreeDTO);
            parentTreeDTO.setId(parent.getId());
            parentTreeDTO.setName(parent.getName());
            //查询权限父级对应的子权限
            List<Permissions> childList = permissionsMapper.selectList(new EntityWrapper<Permissions>().eq("parent_code",parent.getCode()));
            List<PermissionsTreeDTO> childTreeList = new ArrayList<>();
            parentTreeDTO.setChildren(childTreeList);
            //转换成tree结构
            for (Permissions child : childList) {
                PermissionsTreeDTO childTreeDTO = new PermissionsTreeDTO();
                childTreeList.add(childTreeDTO);
                childTreeDTO.setId(child.getId());
                childTreeDTO.setName(child.getName());
            }
        }
        return treeDTO;
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
