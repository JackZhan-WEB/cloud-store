package club.jackzhan.cloudstore.exception;

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

    public BusinessException(String message) {
        super(message);
    }
}
