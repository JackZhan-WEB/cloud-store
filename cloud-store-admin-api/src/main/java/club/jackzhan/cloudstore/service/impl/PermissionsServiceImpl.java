package club.jackzhan.cloudstore.service.impl;

import club.jackzhan.cloudstore.enums.TrueFalseEnum;
import club.jackzhan.cloudstore.module.dto.PermissionsDTO;
import club.jackzhan.cloudstore.module.request.PermissionsRequest;
import club.jackzhan.cloudstore.service.IPermissionsService;
import club.jackzhan.cloudstore.util.BeanUtils;
import club.jackzhan.cloudstore.util.RemoteCallUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

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

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @Override
    public ResultResponse list() {
        return remoteCallUtil.sendGet("/member/permissions/list");
    }

    @Override
    public ResultResponse loadPerms() {
        //1.获取所有的权限表达式
        List<PermissionsDTO> permsList = new ArrayList<>();
        ResultResponse resultResponse = remoteCallUtil.sendGet("/member/permissions/list");
        if (resultResponse.getState()) {
            String json = BeanUtils.bean2Json(resultResponse.getData());
            permsList = BeanUtils.json2List(json,PermissionsDTO.class);
        }

        List<String> codes = new ArrayList<>();
        for (PermissionsDTO item : permsList) {
            codes.add(item.getCode());
        }

        //2.通过RequestMappingHandlerMapping获取所有的贴有requestMapping注解的方法
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        Collection<HandlerMethod> methods = handlerMethods.values();
        List<PermissionsDTO> newPermsList = new ArrayList<>();
        for (HandlerMethod method : methods) {
            //3.判断是否贴有权限注解
            RequiresPermissions anno = method.getMethodAnnotation(RequiresPermissions.class);
            if (anno != null) {
                //4.保存权限表达式
                String[] value = anno.value();
                String code = value[0];

                //判断数据库中是否存在权限 表达式
                if (codes.contains(code)) {
                    continue;
                }
                String permissionName = value[1];
                PermissionsDTO p = new PermissionsDTO();
                newPermsList.add(p);
                p.setName(permissionName);
                p.setCode(code);
                p.setType(TrueFalseEnum.TRUE.getCode());
                p.setCreateTime(new Date());
            }
        }
        PermissionsRequest request = new PermissionsRequest().setList(newPermsList);
        if (remoteCallUtil.sendPost("/member/permissions/saveList",request).getState()) {
            permsList.addAll(newPermsList);
            return ResultResponse.success(permsList);
        }else {
            return ResultResponse.failure("加载失败！");
        }
    }
}
