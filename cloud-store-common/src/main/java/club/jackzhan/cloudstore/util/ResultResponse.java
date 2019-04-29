package club.jackzhan.cloudstore.util;

import club.jackzhan.cloudstore.enums.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author : JackZhan
 * Date: 2018-12-21
 * Time: 17:10
 */
@Data
public class ResultResponse<T> implements Serializable {

    public final static Integer SUCCESS_CODE = ErrorCodeEnum.NORMAL.getCode();
    public final static String DEFAULT_SUCCESS_MESSAGE = "调用成功";
    public final static Integer FAILURE_CODE = ErrorCodeEnum.SERVICE_ERROR.getCode();
    public final static String DEFAULT_FAILURE_MESSAGE = "业务异常,请稍候重试";

    private T data;

    private String msg = DEFAULT_SUCCESS_MESSAGE;

    private Boolean state;

    private Integer code = SUCCESS_CODE;

    public void setErrorInfo(ErrorCodeEnum e) {
        this.setCode(e.getCode());
        this.setMsg(e.getDesc());
    }

    public static <T> ResultResponse create() {
        return new ResultResponse<>();
    }

    public static <T> ResultResponse create(T data) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setData(data);
        return response;
    }


    public static <T> ResultResponse success() {
        return ResultResponse.success(null, DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> ResultResponse success(T data) {
        return ResultResponse.success(data, DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> ResultResponse success(T data, String message) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setMsg(message);
        response.setData(data);
        response.setState(Boolean.TRUE);
        return response;
    }

    public static <T> ResultResponse failure() {
        return ResultResponse.failure(DEFAULT_FAILURE_MESSAGE);
    }

    public static <T> ResultResponse failure(String message) {
        return ResultResponse.failure(message, FAILURE_CODE);
    }

    public static <T> ResultResponse failure(T data) {
        return ResultResponse.failure(data, DEFAULT_FAILURE_MESSAGE, FAILURE_CODE);
    }

    public static <T> ResultResponse failure(String message, Integer code) {
        return ResultResponse.failure(null, message, code);
    }

    public static <T> ResultResponse failure(T data, String message, Integer code) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setMsg(message);
        response.setCode(code);
        response.setState(Boolean.FALSE);
        return response;
    }

}

