package club.jackzhan;

import club.jackzhan.cloudstore.cfgbeans.CurrentMemberMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@Slf4j
public class AdminStartApp extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        ApplicationContext  ctx = SpringApplication.run(AdminStartApp.class, args);
        //所有的bean,参考：http://412887952-qq-com.iteye.com/blog/2314051
//        String[] beanNames = ctx.getBeanDefinitionNames();
        String[] beanNames = ctx.getBeanNamesForAnnotation(RestController.class);//所有添加该注解的bean
        log.info("==========  bean总数:{}  ===========", ctx.getBeanDefinitionCount());
        int i = 0;
        for (String str : beanNames) {
            log.info("{},beanName:{}", ++i, str);
        }
        log.info("===========  启动成功  ===========");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new CurrentMemberMethodArgumentResolver());
    }
}
