package club.jackzhan.cloudstore.util;

import club.jackzhan.cloudstore.enums.ErrorCodeEnum;
import club.jackzhan.cloudstore.exception.BusinessException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-18 15:47
 *
 * @Author: JackZhan
 */
final public class AssertUtil {

    private AssertUtil() {
    }

    public static void isNotNull(ResultResponse response, Object o, String msg) {
        if (o == null) {
            response.setMsg(msg);
            response.setCode(ErrorCodeEnum.SERVICE_ERROR.getCode());
            throw new NullPointerException(msg);
        }
    }

    public static void isNotNull(ResultResponse response, Object o, ErrorCodeEnum e) {
        if (o == null) {
            response.setErrorInfo(e);
            throw new NullPointerException(e.getDesc());
        }
    }

    public static void isNotNullForList(ResultResponse response, List list, ErrorCodeEnum e) {
        if (list == null || list.isEmpty()) {
            response.setErrorInfo(e);
            throw new NullPointerException(e.getDesc());
        }
    }


}
