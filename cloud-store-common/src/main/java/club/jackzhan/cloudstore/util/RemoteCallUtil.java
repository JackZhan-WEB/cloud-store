package club.jackzhan.cloudstore.util;

import club.jackzhan.cloudstore.module.request.BaseRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResultResponse sendGet(String path) {
        return sendGet(path,null);
    }

    public ResultResponse sendGet(String path, BaseRequest request) {
        Map<String, Object> params = BeanUtils.beanToMap(request);
        StringBuilder sb = new StringBuilder(50);
        if (params != null && params.size() != 0) {
            sb.append("?");
            params.keySet().forEach(e -> {
                sb.append(e).append("=").append(params.get(e)).append("&");
            });
            sb.deleteCharAt(sb.length() - 1);
        }
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