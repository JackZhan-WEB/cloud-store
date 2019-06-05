package club.jackzhan.cloudstore.exception;

import club.jackzhan.cloudstore.constant.BusinessConstant;
import club.jackzhan.cloudstore.enums.ErrorCodeEnum;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-18 15:52
 *
 * @Author: JackZhan
 */
public class BusinessException extends RuntimeException{

    public BusinessException() {
        super();
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getDesc()+ BusinessConstant.BUSINESS_EXCEPTION_SPLIT +errorCodeEnum.getCode());
    }

    public BusinessException(String message) {
        super(message);
    }
}
