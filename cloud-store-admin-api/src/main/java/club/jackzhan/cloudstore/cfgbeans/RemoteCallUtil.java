package club.jackzhan.cloudstore.cfgbeans;

import club.jackzhan.cloudstore.util.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-05-16 16:34
 *
 * @Author: JackZhan
 */
public class RemoteCallUtil {

    @Autowired
    RemoteCallUtil remoteCallUtil;

    @Resource
    private RestTemplate restTemplate;

    private static final String MEMBER_REST_URL_PREFIX = "http://CLOUD-STORE-MEMBER";

    public ResultResponse sendGet(String path, Map<String, Object> params) {


        StringBuilder sb = new StringBuilder(50);
        sb.append("?");
        params.keySet().forEach(e -> {
            sb.append(e).append("=").append(params.get(e)).append("&");
        });
        sb.deleteCharAt(sb.length() - 1);
        return restTemplate.getForEntity(matchingServePath(path) + path + sb.toString(), ResultResponse.class).getBody();
    }

    private String matchingServePath(String path) {
        String prefix = path.split("/")[1];
        if ("member".equals(prefix)) {
            return MEMBER_REST_URL_PREFIX;
        }
        return null;
    }


}
