package club.jackzhan.cloudstore.util;

import club.jackzhan.cloudstore.module.request.common.BaseRequest;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
@Slf4j
public class RemoteCallUtil {

    @Autowired
    RemoteCallUtil remoteCallUtil;

    @Resource
    private RestTemplate restTemplate;

    private static final String MEMBER_REST_URL_PREFIX = "http://CLOUD-STORE-MEMBER/";

    public ResultResponse sendGet(String path) {
        return sendGet(path, null);
    }

    public ResultResponse sendGet(String path, BaseRequest request) {
        Map<String, Object> params = BeanUtils.beanToMap(request);
        StringBuilder sb = new StringBuilder(50);
        if (params != null && params.size() != 0) {
            sb.append("?");
            for (String e : params.keySet()) {
                if (params.get(e) == null) {
                    continue;
                }
                sb.append(e).append("=").append(params.get(e)).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        path = path.replace(".","/");
        return restTemplate.getForEntity(matchingServePath(path) + path + sb.toString(), ResultResponse.class).getBody();
    }

    private String matchingServePath(String path) {
        String prefix = path.split("/")[0];
        if ("member".equals(prefix)) {
            return MEMBER_REST_URL_PREFIX;
        }
        return null;
    }

    public ResultResponse sendPost(String path, BaseRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(BeanUtils.beanToJsonStringWithDateFormat(request), headers);
        path = path.replace(".","/");
        return restTemplate.postForEntity(matchingServePath(path) + path, entity, ResultResponse.class).getBody();
    }
}
