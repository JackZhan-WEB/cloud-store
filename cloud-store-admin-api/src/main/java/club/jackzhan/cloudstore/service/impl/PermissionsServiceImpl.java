package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.enums.TrueFalseEnum;
import club.jackzhan.cloudstore.module.dto.PermissionsDTO;
import club.jackzhan.cloudstore.module.dto.PermissionsTreeDTO;
import club.jackzhan.cloudstore.module.request.PermissionsRequest;
import club.jackzhan.cloudstore.module.request.common.BaseIdRequest;
import club.jackzhan.cloudstore.service.IPermissionsService;
import club.jackzhan.cloudstore.util.AnnoManageUtil;
import club.jackzhan.cloudstore.util.BeanUtils;
import club.jackzhan.cloudstore.util.RemoteCallUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 14:40
 *
 * @Author: JackZhan
 */
@Service("permissionsService")
public class PermissionsServiceImpl implements IPermissionsService {


    @Autowired
    private RemoteCallUtil remoteCallUtil;

    @Override
    public ResultResponse list() {
        return remoteCallUtil.sendGet("member.permissions.list");
    }

    @Override
    public ResultResponse loadPerms() {
        ResultResponse resultResponse = remoteCallUtil.sendGet("member.permissions.getAllCodes");
        JSONArray codes = new JSONArray();
        if (resultResponse.getState()) {
            String json = BeanUtils.bean2Json(resultResponse.getData());
            codes = JSON.parseArray(json);
        }

        List<PermissionsDTO> newPermsList = new ArrayList<>();

        List<List<RequiresPermissions>> annotations = AnnoManageUtil.getAnnotations("club.jackzhan.cloudstore.web.controller", RequiresPermissions.class);
        for (List<RequiresPermissions> annotation : annotations) {
            boolean flag = false;
            String parentCdoe = "";
            for (RequiresPermissions anno : annotation) {
                if (anno != null) {
                    //4.保存权限表达式
                    String[] value = anno.value();
                    String code = value[0];

                    PermissionsDTO p = new PermissionsDTO();
                    if(!flag){
                        flag = true;
                        parentCdoe = code;
                    }else {
                        p.setParentCode(parentCdoe);
                    }
                    //判断数据库中是否存在权限 表达式
                    if (codes.contains(code)) {
                        continue;
                    }
                    String permissionName = value[1];
                    newPermsList.add(p);
                    p.setName(permissionName);
                    p.setCode(code);
                    p.setType(TrueFalseEnum.TRUE.getCode());
                    p.setCreateTime(new Date());
                }
            }
        }
        PermissionsRequest request = new PermissionsRequest().setList(newPermsList);;

        if (remoteCallUtil.sendPost("member.permissions.saveList",request).getState()) {
            return ResultResponse.success();
        }else {
            return ResultResponse.failure("加载失败！");
        }
    }

    @Override
    public ResultResponse getPerms() {
        return remoteCallUtil.sendGet("member.permissions.getPerms");
    }

    @Override
    public ResultResponse getCheckPerms(BaseIdRequest<Integer> request) {
        return remoteCallUtil.sendGet("member.permissions.getCheckPerms",request);
    }
}
