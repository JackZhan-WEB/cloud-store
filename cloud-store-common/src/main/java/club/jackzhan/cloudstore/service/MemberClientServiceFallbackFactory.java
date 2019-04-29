package club.jackzhan.cloudstore.service;

import club.jackzhan.cloudstore.module.entities.Member;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component // 不要忘记添加，不要忘记添加
public class MemberClientServiceFallbackFactory implements FallbackFactory<MemberClientService> {
    @Override
    public MemberClientService create(Throwable cause) {
        return new MemberClientService() {

            @Override
            public Member getByLoginName(String loginName) {
                return null;
            }
        };
    }
}
