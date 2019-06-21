import club.jackzhan.AdminStartApp;
import club.jackzhan.cloudstore.enums.TrueFalseEnum;
import club.jackzhan.cloudstore.module.dto.PermissionsDTO;
import club.jackzhan.cloudstore.util.AnnoManageUtil;
import club.jackzhan.cloudstore.util.BeanUtils;
import club.jackzhan.cloudstore.util.RemoteCallUtil;
import club.jackzhan.cloudstore.util.ResultResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminStartApp.class)
public class TestController {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private RemoteCallUtil remoteCallUtil;

    @Test
    public void reload() {
        //获取数据库中所有的权限表达式
//        List<String> exprs = permissionMapper.selectExpressions();
        //获取所有的controller
        Collection<Object> ctrs = ctx.getBeansWithAnnotation(RestController.class).values();
        //获取controller中贴有权限注解的方法
        for (Object ctr : ctrs) {
            Annotation[] annotations = ctr.getClass().getAnnotations();
            RequiresPermissions parentPerms = ctr.getClass().getAnnotation(RequiresPermissions.class);
            if (parentPerms==null) {
                continue;
            }
            String[] parentValues = parentPerms.value();
            System.out.println("========");
            System.out.println(parentValues[0]);
            Method[] ms = ctr.getClass().getDeclaredMethods();
            for (Method m : ms) {
                RequiresPermissions anno = m.getAnnotation(RequiresPermissions.class);
                if (anno != null) {//如果不为null,说明贴有权限注解
                    String[] value = anno.value();
                    System.out.println(value[0]);
//                    String expression = PermissionUtil.BuildExpression(m);
//                    if (!exprs.contains(expression)) {
//                        //保存权限表达式
////                        Permission per = new Permission();
////                        per.setExpression(expression);
////                        per.setName(anno.value());
////                        permissionMapper.insert(per);
//                    }
                }
            }
        }
    }


    @Test
    public void getAnnotations(){

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

        List<PermissionsDTO> newPermsList = new ArrayList<>();

        List<List<RequiresPermissions>> annotations = AnnoManageUtil.getAnnotations("club.jackzhan.cloudstore.web.controller", RequiresPermissions.class);
        for (List<RequiresPermissions> annotation : annotations) {
            for (RequiresPermissions anno : annotation) {
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
        }

        permsList.addAll(newPermsList);

        System.out.println(permsList);
    }


}
