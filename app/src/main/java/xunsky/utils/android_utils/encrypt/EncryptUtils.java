package xunsky.utils.android_utils.encrypt;

import android.util.Base64;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public final class EncryptUtils {
    private EncryptUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    ///////////////////////////////////////////////////////////////////////////
    // 哈希加密相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * MD2加密
     */
    public static String getMd2String(final String data) {
        return getMd2String(data.getBytes());
    }
    public static String getMd2String(final byte[] data) {
        return bytes2HexString(getMd2Bytes(data));
    }
    public static byte[] getMd2Bytes(final byte[] data) {
        return hashTemplate(data, "MD2");
    }

    /**
     * MD5加密
     */
    public static String getMd5String(final String data) {
        return getMd5String(data.getBytes());
    }
    public static String getMd5String(final byte[] data) {
        return bytes2HexString(getMd5Bytes(data));
    }
    public static String getMd5String(final String data, final String salt) {
        return bytes2HexString(getMd5Bytes((data + salt).getBytes()));
    }
    public static String getMd5String(final byte[] data, final byte[] salt) {
        if (data == null || salt == null) return null;
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return bytes2HexString(getMd5Bytes(dataSalt));
    }
    public static byte[] getMd5Bytes(final byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * MD5加密文件
     * 返回文件的十六进制密文
     */
    public static String getFileMd5String(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return getFileMd5String(file);
    }
    public static String getFileMd5String(final File file) {
        return bytes2HexString(getFileMd5Code(file));
    }

    /**
     * 获取文件的MD5校验码
     */
    public static byte[] getFileMd5Code(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return getFileMd5Code(file);
    }
    public static byte[] getFileMd5Code(final File file) {
        if (file == null) return null;
        FileInputStream fis = null;
        DigestInputStream digestInputStream;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (!(digestInputStream.read(buffer) > 0)) break;
            }
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(fis);
        }
    }

    /**
     * SHA1加密
     */
    public static String getSha1String(final String data) {
        return getSha1String(data.getBytes());
    }
    public static String getSha1String(final byte[] data) {
        return bytes2HexString(getSha1Bytes(data));
    }
    public static byte[] getSha1Bytes(final byte[] data) {
        return hashTemplate(data, "SHA1");
    }

    /**
     * SHA224加密
     */
    public static String getSha224String(final String data) {
        return getSha224String(data.getBytes());
    }
    public static String getSha224String(final byte[] data) {
        return bytes2HexString(getSha224bytes(data));
    }
    public static byte[] getSha224bytes(final byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /**
     * SHA256加密
     */
    public static String getSha256String(final String data) {
        return getSha256String(data.getBytes());
    }
    public static String getSha256String(final byte[] data) {
        return bytes2HexString(getDatas(data));
    }
    public static byte[] getDatas(final byte[] data) {
        return hashTemplate(data, "SHA256");
    }

    /**
     * SHA384加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String getSha384String(final String data) {
        return getSha384String(data.getBytes());
    }
    public static String getSha384String(final byte[] data) {
        return bytes2HexString(getSha384Datas(data));
    }
    public static byte[] getSha384Datas(final byte[] data) {
        return hashTemplate(data, "SHA384");
    }

    /**
     * SHA512加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String getSha512String(final String data) {
        return getSha512String(data.getBytes());
    }
    public static String getSha512String(final byte[] data) {
        return bytes2HexString(getSha512Bytes(data));
    }
    public static byte[] getSha512Bytes(final byte[] data) {
        return hashTemplate(data, "SHA512");
    }



    /**
     * HmacMD5加密
     * @return 16进制密文
     */
    public static String getHmacMD5String(final String data, final String key) {
        return getHmacMD5String(data.getBytes(), key.getBytes());
    }
    public static String getHmacMD5String(final byte[] data, final byte[] key) {
        return bytes2HexString(getHmacMD5Bytes(data, key));
    }
    public static byte[] getHmacMD5Bytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacMD5");
    }

    /**
     * HmacSHA1加密
     * @return 16进制密文
     */
    public static String getHmacSha1String(final String data, final String key) {
        return getHmacSha1String(data.getBytes(), key.getBytes());
    }
    public static String getHmacSha1String(final byte[] data, final byte[] key) {
        return bytes2HexString(getHmacSha1Bytes(data, key));
    }
    public static byte[] getHmacSha1Bytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA1");
    }

    /**
     * HmacSHA224加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String getHmacSha224String(final String data, final String key) {
        return getHmacSha224String(data.getBytes(), key.getBytes());
    }
    public static String getHmacSha224String(final byte[] data, final byte[] key) {
        return bytes2HexString(getHmacSha224Bytes(data, key));
    }
    public static byte[] getHmacSha224Bytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA224");
    }

    /**
     * HmacSHA256加密
     * @return 16进制密文
     */
    public static String getHmacSha256String(final String data, final String key) {
        return getHmacSha256String(data.getBytes(), key.getBytes());
    }
    public static String getHmacSha256String(final byte[] data, final byte[] key) {
        return bytes2HexString(getHmacSha256Bytes(data, key));
    }
    public static byte[] getHmacSha256Bytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA256");
    }

    /**
     * HmacSHA384加密
     * @return 16进制密文
     */
    public static String getHmacSha384String(final String data, final String key) {
        return getHmacSha384String(data.getBytes(), key.getBytes());
    }
    public static String getHmacSha384String(final byte[] data, final byte[] key) {
        return bytes2HexString(getHmacSha384Bytes(data, key));
    }
    public static byte[] getHmacSha384Bytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA384");
    }

    /**
     * HmacSHA512加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16进制密文
     */
    public static String getHmacSha512String(final String data, final String key) {
        return getHmacSha512String(data.getBytes(), key.getBytes());
    }
    public static String getHmacSha512String(final byte[] data, final byte[] key) {
        return bytes2HexString(getHmacSha512Bytes(data, key));
    }
    public static byte[] getHmacSha512Bytes(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA512");
    }

    ///////////////////////////////////////////////////////////////////////////
    // DES加密相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * DES转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static        String DES_Transformation = "DES/ECB/NoPadding";
    private static final String DES_Algorithm      = "DES";

    /**
     * DES加密
     */
    public static byte[] getDesAndTranslate2Base64String(final byte[] data, final byte[] key) {
        return base64Encode(getDesBytes(data, key));
    }
    public static String getDesAndTranslate2HexString(final byte[] data, final byte[] key) {
        return bytes2HexString(getDesBytes(data, key));
    }
    public static byte[] getDesBytes(final byte[] data, final byte[] key) {
        return desTemplate(data, key, DES_Algorithm, DES_Transformation, true);
    }

    /**
     * DES解密
     */
    public static byte[] decryptBase64DES(final byte[] data, final byte[] key) {
        return decryptDES(base64Decode(data), key);
    }
    public static byte[] decryptHexStringDES(final String data, final byte[] key) {
        return decryptDES(hexString2Bytes(data), key);
    }
    public static byte[] decryptDES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, DES_Algorithm, DES_Transformation, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 3DES加密相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 3DES转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static        String TripleDES_Transformation = "DESede/ECB/NoPadding";
    private static final String TripleDES_Algorithm      = "DESede";


    /**
     * 3DES加密
     */
    public static byte[] get3DesAndTranslate2Base64Bytes(final byte[] data, final byte[] key) {
        return base64Encode(get3DesString(data, key));
    }
    public static String get3DesAndTranslate2HexString(final byte[] data, final byte[] key) {
        return bytes2HexString(get3DesString(data, key));
    }
    public static byte[] get3DesString(final byte[] data, final byte[] key) {
        return desTemplate(data, key, TripleDES_Algorithm, TripleDES_Transformation, true);
    }

    /**
     * 3Des解密
     */
    public static byte[] decrypt3DesBase64Bytes(final byte[] data, final byte[] key) {
        return decrypt3DES(base64Decode(data), key);
    }
    public static byte[] decrypt3DesHexString(final String data, final byte[] key) {
        return decrypt3DES(hexString2Bytes(data), key);
    }
    public static byte[] decrypt3DES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, TripleDES_Algorithm, TripleDES_Transformation, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // AES加密相关
    ///////////////////////////////////////////////////////////////////////////

    /**
     * AES转变
     * <p>法算法名称/加密模式/填充方式</p>
     * <p>加密模式有：电子密码本模式ECB、加密块链模式CBC、加密反馈模式CFB、输出反馈模式OFB</p>
     * <p>填充方式有：NoPadding、ZerosPadding、PKCS5Padding</p>
     */
    public static        String AES_Transformation = "AES/ECB/NoPadding";
    private static final String AES_Algorithm      = "AES";


    /**
     * AES加密
     */
    public static byte[] getAesAndTranslate2BaseBytes(final byte[] data, final byte[] key) {
        return base64Encode(encryptAES(data, key));
    }
    public static String getAesAndTranslate2HexString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptAES(data, key));
    }
    public static byte[] encryptAES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, AES_Algorithm, AES_Transformation, true);
    }

    /**
     * AES解密
     */
    public static byte[] decryptAesBase64Bytes(final byte[] data, final byte[] key) {
        return decryptAES(base64Decode(data), key);
    }
    public static byte[] decryptAesHexString(final String data, final byte[] key) {
        return decryptAES(hexString2Bytes(data), key);
    }
    public static byte[] decryptAES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, AES_Algorithm, AES_Transformation, false);
    }


    /**
     * 工具方法相关
     */
    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    private static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据key获取对应的hash加密算法
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 根据key获取对应的hmac加密算法
     */
    private static byte[] hmacTemplate(final byte[] data, final byte[] key, final String algorithm) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DES加密模板
     *
     * @param data           数据
     * @param key            秘钥
     * @param algorithm      加密算法
     * @param transformation 转变
     * @param isEncrypt      {@code true}: 加密 {@code false}: 解密
     * @return 密文或者明文，适用于DES，3DES，AES
     */
    public static byte[] desTemplate(final byte[] data, final byte[] key, final String algorithm, final String transformation, final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            SecureRandom random = new SecureRandom();
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, random);
            return cipher.doFinal(data);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
            }
        }
        return true;
    }
}
