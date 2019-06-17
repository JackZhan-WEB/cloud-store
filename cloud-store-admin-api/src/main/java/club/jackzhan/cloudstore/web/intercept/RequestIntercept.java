package club.jackzhan.cloudstore.web.intercept;

import club.jackzhan.cloudstore.constant.BusinessConstant;
import club.jackzhan.cloudstore.constant.Constants;
import club.jackzhan.cloudstore.enums.ErrorCodeEnum;
import club.jackzhan.cloudstore.exception.BusinessException;
import club.jackzhan.cloudstore.module.request.CurrentMember;
import club.jackzhan.cloudstore.util.BeanUtils;
import club.jackzhan.cloudstore.util.RedisOperation;
import club.jackzhan.cloudstore.util.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 全局请求处理
 * Date: 2019-05-23 10:50
 *
 * @Author: JackZhan
 */
@Component
@Aspect
@Slf4j
public class RequestIntercept {


    @Autowired
    private RedisOperation redisOperation;
    @Autowired
    private WebApplicationContext applicationContext;

    /**
     * Define a pointcut
     */
//    @Pointcut("@annotation(club.jackzhan.cloudstore.annotation.SystemLog)")
    @Pointcut("execution(* club.jackzhan.cloudstore.web.controller.*.*(..))")
    public void controllerLog() {
    }

    /**
     * Print Log before controller
     *
     * @param joinPoint
     */
    @Before("controllerLog()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String token = request.getHeader(Constants.TOKEN);
        StringBuffer requestURL = request.getRequestURL();
        if (!requestURL.toString().contains("/login/auth")) {
            if(!redisOperation.hasKey(token)){
                throw new BusinessException(ErrorCodeEnum.MEMBER_SESSION_TIME_OUT);
            }
            redisOperation.expire(token, BusinessConstant.TOKEN_EXPIRE_TIME);
            CurrentMember currentMember = BeanUtils.json2Bean((String) redisOperation.get(token), CurrentMember.class);
            UserThreadLocal.set(currentMember);
        }

//        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
//        // 获取url与类和方法的对应信息
//        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
//
//        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
//            RequestMappingInfo info = m.getKey();
//            HandlerMethod method = m.getValue();
//            PatternsRequestCondition p = info.getPatternsCondition();
//            for (String url : p.getPatterns()) {
//                if(request.getRequestURI().equals(url)){
//                    MethodParameter[] methodParameters = method.getMethodParameters();
//                    for (int i = 0; i < methodParameters.length; i++) {
//                        if (CurrentMember.class.equals(methodParameters[i].getParameterType())) {
//                            log.error("methodParameters{}",methodParameters[i].getParameterType());
//                        }
//                    }
//
//                    log.info("接口参数：{}", methodParameters);
//
//                }
//            }
////            map1.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
////            map1.put("method", method.getMethod().getName()); // 方法名
////            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
////            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
////                map1.put("type", requestMethod.toString());
////            }
//
//        }

        log.info("请求IP：{}", request.getRemoteAddr());
        log.info("请求路径：{}", requestURL);
        log.info("请求方式：{}", request.getMethod());
        log.info("请求参数：{}", joinPoint.getArgs());
    }

    /**
     * Print the time that request method execution spend
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("controllerLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        Object retVal = joinPoint.proceed(args);
        long endTime = System.currentTimeMillis();
        log.info("执行时间：{} ms", endTime - startTime);
        log.info("返回值：{}\n\t", BeanUtils.bean2Json(retVal));
        return retVal;
    }

}
