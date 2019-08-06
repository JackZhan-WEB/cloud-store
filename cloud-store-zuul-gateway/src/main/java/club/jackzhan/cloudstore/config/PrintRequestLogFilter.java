package club.jackzhan.cloudstore.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import net.sf.json.JSONObject;


@Component
@Slf4j
public class PrintRequestLogFilter extends ZuulFilter {

    @Override
    public String filterType() {
        //要打印返回信息，必须得用"post"
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            log.info("请求IP：{}", request.getRemoteAddr());
            InputStream in = request.getInputStream();
            String reqBbody = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
            // 打印请求方法，路径
            log.info("请求路径:\t" + request.getMethod() + "\t" + request.getRequestURL().toString());
            Map<String, String[]> map = request.getParameterMap();
            // 打印请求url参数
            if (map != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("request parameters:\t");
                for (Map.Entry<String, String[]> entry : map.entrySet()) {
                    sb.append("[").append(entry.getKey()).append("=").append(printArray(entry.getValue())).append("]");
                }
                log.info(sb.toString());
            }
            // 打印请求json参数
            log.info("request body:\t" + reqBbody);

            // 打印response
            InputStream out = ctx.getResponseDataStream();
            String outBody = StreamUtils.copyToString(out, Charset.forName("UTF-8"));
            log.info("返回值:\t" + outBody);

            //重要！！！
            ctx.setResponseBody(outBody);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    String printArray(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
