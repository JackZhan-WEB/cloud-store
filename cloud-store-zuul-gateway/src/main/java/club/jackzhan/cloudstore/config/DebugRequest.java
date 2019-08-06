package club.jackzhan.cloudstore.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

//@Component
@Slf4j
public class DebugRequest extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 1;
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
        HttpServletRequest req = (HttpServletRequest) RequestContext.getCurrentContext().getRequest();

        log.info("请求IP：{}", req.getRemoteAddr());
        log.info("请求路径：{}", req.getRequestURL());
        log.info("请求方式：{}", req.getMethod());
        log.info("请求参数：{}", req.getQueryString());

//        System.err.println("REQUEST:: " + req.getScheme() + " " + req.getRemoteAddr() + ":" + req.getRemotePort());
//        System.err.println("REQUEST:: " + req.getScheme() + " " + req.getRemoteAddr() + ":" + req.getRemotePort());
//        StringBuilder params = new StringBuilder("?");
//        Enumeration<String> names = req.getParameterNames();
//        if( req.getMethod().equals("GET") ) {
//           while (names.hasMoreElements()) {
//                 String name = (String) names.nextElement();
//                 params.append(name);
//                 params.append("=");
//                 params.append(req.getParameter(name));
//                 params.append("&");
//              }
//        }
//        if (params.length() > 0) {
//            params.delete(params.length()-1, params.length());
//        }
//        System.err.println("REQUEST:: > " + req.getMethod() + " " + req.getRequestURI() + params + " " + req.getProtocol());
//        Enumeration<String> headers = req.getHeaderNames();
//        while (headers.hasMoreElements()) {
//            String name = (String) headers.nextElement();
//            String value = req.getHeader(name);
//            System.err.println("REQUEST:: > " + name + ":" + value);
//        }
//        final RequestContext ctx = RequestContext.getCurrentContext();
//        if (!ctx.isChunkedRequestBody()) {
//            ServletInputStream inp = null;
//            try {
//                inp = ctx.getRequest().getInputStream();
//                String body = null;
//                if (inp != null) {
//                    body = IOUtils.toString(inp);
//                    System.err.println("REQUEST:: > " + body);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return null;
    }
}
