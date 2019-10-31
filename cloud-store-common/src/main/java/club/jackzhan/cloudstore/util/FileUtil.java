package club.jackzhan.cloudstore.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <br>
 * <b>Function：</b><br>
 * <b>Author：</b>@author ziyou<br>
 * <b>Date：</b>2019-10-24 11:16<br>
 * <b>Desc：</b>无<br>
 */
public class FileUtil {

    /**
     * 获取文件投
     *
     * @param filePath 文件路径
     * @return 16 进制的文件投信息
     * @throws IOException
     */
    private static String getFileHeader(String filePath) throws IOException {
        byte[] b = new byte[28];
        InputStream inputStream = new FileInputStream(filePath);
        inputStream.read(b, 0, 28);
        inputStream.close();
        return bytes2hex(b);
    }

    /**
     * 将字节数组转换成16进制字符串
     */
    private static String bytes2hex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据文件路径获取文件类型
     *
     * @param filePath 文件路径
     * @return 文件类型
     * @throws IOException
     */
    public static FileType getFileType(String filePath) throws IOException {
        String fileHead = getFileHeader(filePath);
        if (null == fileHead || fileHead.length() == 0) {
            return null;
        }
        fileHead = fileHead.toUpperCase();
        FileType[] fileTypes = FileType.values();
        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
//        String filePath = "/Users/ziyou/Downloads/SpringBoot实战.pdf";
        String filePath = "/Users/ziyou/Downloads/SpringBoot实战.png";
        FileType fileType = getFileType(filePath);
        System.out.println(fileType.getKey());
    }
}
