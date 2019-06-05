import club.jackzhan.AdminStartApp;
import club.jackzhan.cloudstore.module.dto.RoleDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminStartApp.class)
public class TestController {

    @Autowired
    private RedisTemplate<String,RoleDTO> redisTemplate;

    @Test
    public void test() {

//        RoleDTO role = new RoleDTO().setId(2).setName("测试2").setDescription("ceshi2");

//        redisOperation.set("222","aaa");
//        redisOperation.hset("role","role2",role);
//        Map<Object, Object> role = redisOperation.hmget("role");
//        System.out.println(role);
//        redisOperation.del("111");
//        redisTemplate.opsForValue().set("asdf","zvbasd");

//        HashOperations<String,String,RoleDTO> hashOperations = redisTemplate.opsForHash();
//        Map<String, RoleDTO> role = hashOperations.entries("role");
//        System.out.println(role);
        String clazzName3 = new Object() {
            public String getClassName() {
                String clazzName = this.getClass().getName();
                return clazzName.substring(0, clazzName.lastIndexOf('$'));
            }
        }.getClassName();
        System.out.println(clazzName3);
    }





}
