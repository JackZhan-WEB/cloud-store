package club.jackzhan.cloudstore.cfgbeans;

import club.jackzhan.cloudstore.constant.Constants;
import club.jackzhan.cloudstore.module.request.CurrentMember;
import club.jackzhan.cloudstore.util.MemberUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 将当前用户的登陆信息注入到CurrentMember对象中
 *
 * @author JackZhan
 */
public class CurrentMemberMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter arg0, ModelAndViewContainer arg1, NativeWebRequest arg2, WebDataBinderFactory arg3) throws Exception {
        return MemberUtil.getCurrentMember();
    }

    @Override
    public boolean supportsParameter(MethodParameter arg0) {
        return arg0.getParameterType().equals(CurrentMember.class);
    }

}
