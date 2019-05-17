package club.jackzhan.cloudstore.config;

import club.jackzhan.cloudstore.web.interceptor.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-17 19:04
 *
 * @Author: JackZhan
 */
@Configuration
public class ConfigBeans {

    @Bean
    public Interceptor getInterceptor(){
        return new PageInterceptor();
    }
}
