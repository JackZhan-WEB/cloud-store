package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.entities.Member;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-17 17:05
 *
 * @Author: JackZhan
 */
@FeignClient(value = "CLOUD-STORE-MEMBER",fallbackFactory=MemberClientServiceFallbackFactory.class)
public interface MemberClientService {


    /**
     * 根据用户的登陆名获取用户信息
     * @param loginName
     * @return
     */
    Member getByLoginName(String loginName);


}
