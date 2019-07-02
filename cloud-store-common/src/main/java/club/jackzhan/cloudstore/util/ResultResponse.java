package club.jackzhan.cloudstore.util;

import club.jackzhan.cloudstore.constant.BusinessConstant;
import club.jackzhan.cloudstore.enums.ErrorCodeEnum;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

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

    private final static Integer DEFAULT_SUCCESS_CODE = ErrorCodeEnum.NORMAL.getCode();
    private final static String DEFAULT_SUCCESS_MESSAGE = ErrorCodeEnum.NORMAL.getDesc();
    private final static Integer DEFAULT_FAILURE_CODE = ErrorCodeEnum.SERVICE_ERROR.getCode();
    private final static String DEFAULT_FAILURE_MESSAGE = ErrorCodeEnum.SERVICE_ERROR.getDesc();

    private T data;

    private String msg = DEFAULT_SUCCESS_MESSAGE;

    private Boolean state;

    private Integer code = DEFAULT_SUCCESS_CODE;

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

    public static <T> ResultResponse success(ErrorCodeEnum codeEnum) {
        return ResultResponse.success(codeEnum.getDesc(), null, codeEnum.getCode());
    }

    public static <T> ResultResponse success(T data) {
        return ResultResponse.success(data, DEFAULT_SUCCESS_MESSAGE);
    }


    public static <T> ResultResponse success(T data, String message) {
        return ResultResponse.success(message,data,DEFAULT_SUCCESS_CODE);
    }

    public static <T> ResultResponse success(String message, T data, Integer code) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setMsg(message);
        response.setCode(code);
        response.setData(data);
        response.setState(Boolean.TRUE);
        return response;
    }

    public static <T> ResultResponse failure() {
        return ResultResponse.failure(DEFAULT_FAILURE_MESSAGE);
    }

    public static <T> ResultResponse failure(String message) {
        String[] err = message.split(BusinessConstant.BUSINESS_EXCEPTION_SPLIT);
        if (err.length==2) {
            return ResultResponse.failure(err[0], Integer.valueOf(err[1]));
        }
        return ResultResponse.failure(message, DEFAULT_FAILURE_CODE);
    }

    public static <T> ResultResponse failure(ErrorCodeEnum codeEnum) {
        return ResultResponse.failure(codeEnum.getDesc(), codeEnum.getCode());
    }

    public static <T> ResultResponse failure(String message, Integer code) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setMsg(message);
        response.setCode(code);
        response.setState(Boolean.FALSE);
        return response;
    }
}

