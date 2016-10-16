package support.base.util;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import static java.util.Locale.US;

/**
 * @author adison
 * @describe MD5生成帮助类.
 * @date: 2014-10-22 下午3:40:34 <br/>
 */
public class MD5Utils {

    /**
     * 生成hash的长度
     */
    private static final int HASH_LENGTH = 32;

    /**
     * 编码方式
     */
    private static final String CHARSET = "UTF-8"; //"CP1252";

    private static final MessageDigest MD5;

    static {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            digest = null;
        }
        MD5 = digest;
    }

    public static byte[] md5Digest(final String value) {
        if (MD5 == null)
            return null;

        byte[] bytes;
        try {
            bytes = value.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        synchronized (MD5) {
            MD5.reset();
            bytes = MD5.digest(bytes);
        }
        return bytes;
    }

    private static String digest(final String value) {
        if (MD5 == null)
            return null;

        byte[] bytes;
        try {
            bytes = value.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        synchronized (MD5) {
            MD5.reset();
            bytes = MD5.digest(bytes);
        }

        String hashed = new BigInteger(1, bytes).toString(16);
        int padding = HASH_LENGTH - hashed.length();
        if (padding == 0)
            return hashed;

        char[] zeros = new char[padding];
        Arrays.fill(zeros, '0');
        return new StringBuilder(HASH_LENGTH).append(zeros).append(hashed).toString().toLowerCase(US);
    }

    /**
     * 根据具体字符串获取MD5值
     *
     * @param str
     * @return hash
     */
    public static String getMD5(String str) {
        if (StringUtils.isEmpty(str))
            return null;
        str = str.trim();
        return str.length() > 0 ? digest(str) : null;
    }

    /**
     * 获取文件MD5码
     *
     * @param file
     * @return MD5码
     */
    public static String getFileMD5(File file) {
        if (null == file || !file.isFile()) {
            return null;
        }

        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // BugFixed: 这种方法：会将以'0'开头的字节丢弃了；导致MD5码错误；
//        BigInteger bigInt = new BigInteger(1, digest.digest());
//        return bigInt.toString(16);

        // 改用如下方法实现
        return byteArrayToHex(digest.digest());
    }

    /**
     * 第二种获取文件MD5码的实现方法
     *
     * @param inputFile
     * @return MD5码
     */
    public static String fileMD5(String inputFile) {
        // 缓冲区大小（这个可以抽出一个参数）
        int bufferSize = 1 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;


        try {
            // 拿到一个MD5转换器（同样，这里可以换成SHA1）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            // 使用DigestInputStream
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);

            // read的过程中进行MD5处理，直到读完文件
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0) ;

            // 获取最终的MessageDigest
            messageDigest = digestInputStream.getMessageDigest();

            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();

            // 同样，把字节数组转换成字符串
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                digestInputStream.close();
            } catch (Exception e) {
            }

            try {
                fileInputStream.close();
            } catch (Exception e) {
            }
        }

    }

    private static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];


        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;

        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }

        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
    
    
}