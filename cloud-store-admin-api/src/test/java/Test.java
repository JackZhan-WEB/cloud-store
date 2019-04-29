import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2019-04-22 15:06
 *
 * @Author: JackZhan
 */
public class Test {
    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = "123456";//密码原值
        Object salt = "123";//盐值
        int hashIterations = 1;//加密1024次
        Object result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);
        System.out.println(result);
    }
}
