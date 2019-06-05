package club.jackzhan.cloudstore.web.intercept;

import club.jackzhan.cloudstore.constant.BusinessConstant;
import club.jackzhan.cloudstore.enums.ErrorCodeEnum;
import club.jackzhan.cloudstore.exception.BusinessException;
import club.jackzhan.cloudstore.module.dto.MemberDTO;
import club.jackzhan.cloudstore.util.BeanUtils;
import club.jackzhan.cloudstore.util.RedisOperation;
import club.jackzhan.cloudstore.util.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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
    private StringRedisTemplate redisTemplate;

    @Autowired
    RedisOperation redisOperation;


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
    public void before(JoinPoint joinPoint) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String token = request.getHeader("token");
        if (!request.getRequestURL().toString().contains("/login/auth") && redisOperation.hasKey(token)) {
            redisOperation.expire(token, BusinessConstant.TOKEN_EXPIRE_TIME);
        }

        log.info("请求IP：{}", request.getRemoteAddr());
        log.info("请求路径：{}", request.getRequestURL());
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
