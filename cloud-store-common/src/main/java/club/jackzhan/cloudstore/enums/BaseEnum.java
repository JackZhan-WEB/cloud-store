package club.jackzhan.cloudstore.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: JackZhan
 * Date: 2018-12-21
 * Time: 17:14
 */
public interface BaseEnum<T> {
    /**
     * 编码
     * @return
     */
    T getCode();

    /**
     * 描述
     * @return
     */
    String getDesc();


}
