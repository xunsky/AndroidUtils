package xunsky.utils.android_utils.encrypt;

import android.os.Build;
import android.util.Base64;
import android.util.Log;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RSASecurity {
    private static Cipher cipher;
    static {
        try {
            try {
                cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
            } catch (NoSuchProviderException var1) {
                var1.printStackTrace();
            }
        } catch (NoSuchAlgorithmException var2) {
            var2.printStackTrace();
        } catch (NoSuchPaddingException var3) {
            var3.printStackTrace();
        }

    }

    public RSASecurity() {
    }

    public static void main(String[] args) {
        String filePath = "D:/workspace/RM_Android_SDK/";
        generateKeyPair(filePath);
    }

    public static Map<String, String> generateKeyPair(String filePath) {
        try {
            KeyPairGenerator e = KeyPairGenerator.getInstance("RSA");
            e.initialize(1024);
            KeyPair keyPair = e.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
            String publicKeyString = getKeyString(publicKey);
            String privateKeyString = getKeyString(privateKey);
            FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
            FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
            BufferedWriter pubbw = new BufferedWriter(pubfw);
            BufferedWriter pribw = new BufferedWriter(prifw);
            pubbw.write(publicKeyString);
            pribw.write(privateKeyString);
            pubbw.flush();
            pubbw.close();
            pubfw.close();
            pribw.flush();
            pribw.close();
            prifw.close();
            HashMap map = new HashMap();
            map.put("publicKey", publicKeyString);
            map.put("privateKey", privateKeyString);
            return map;
        } catch (Exception var12) {
            var12.printStackTrace();
            return null;
        }
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key, 0);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = Base64.decode(key, 0);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        return new String(Base64.encode(keyBytes, 0), "UTF-8");
    }

    public static String encrypt(String plainText, PublicKey publicKey) {
        try {
            cipher.init(1, publicKey);
            byte[] e = cipher.doFinal(plainText.getBytes("UTF-8"));
            return new String(Base64.encode(e, 0), "UTF-8");
        } catch (InvalidKeyException var3) {
            var3.printStackTrace();
        } catch (IllegalBlockSizeException var4) {
            var4.printStackTrace();
        } catch (BadPaddingException var5) {
            var5.printStackTrace();
        } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
        }

        return null;
    }

    public static String encrypt(String plainText, String publicKeyString) {
        try {
            cipher.init(1, getPublicKey(publicKeyString));
            byte[] e = cipher.doFinal(plainText.getBytes("UTF-8"));
            return new String(Base64.encode(e, 0), "UTF-8");
        } catch (InvalidKeyException var3) {
            var3.printStackTrace();
        } catch (IllegalBlockSizeException var4) {
            var4.printStackTrace();
        } catch (BadPaddingException var5) {
            var5.printStackTrace();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return null;
    }

    public static String decrypt(PrivateKey privateKey, String enStr) {
        try {
            cipher.init(2, privateKey);
            byte[] e = cipher.doFinal(Base64.decode(enStr, 0));
            return new String(e);
        } catch (InvalidKeyException var3) {
            var3.printStackTrace();
        } catch (IllegalBlockSizeException var4) {
            var4.printStackTrace();
        } catch (BadPaddingException var5) {
            var5.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String privateKeyString, String enStr) {
        try {
            cipher.init(2, getPrivateKey(privateKeyString));
            byte[] e = cipher.doFinal(Base64.decode(enStr, 0));
            return new String(e);
        } catch (InvalidKeyException var3) {
            var3.printStackTrace();
        } catch (IllegalBlockSizeException var4) {
            var4.printStackTrace();
        } catch (BadPaddingException var5) {
            var5.printStackTrace();
        } catch (IOException var6) {
            var6.printStackTrace();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return null;
    }

    public static class SecureRandomUtil {
        private static final String CODE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        private static final String TRANSFORMATION = "AES/GCM/NOPADDING";

        private static final String PROVIDER = "BC";

        private static final String ALGORITHM = "AES";

        static {
            // 添加bouncycastle provider，支持AES/GCM/NOPADDING加／解密
            //implementation 'org.bouncycastle:bcpkix-jdk15on:1.59'
            Security.addProvider(new BouncyCastleProvider());
        }



        /**
         * AES加密
         * @param content   待加密内容
         * @param key       密钥
         * @param iv        初始化向量
         * @return          经过AES加密后的文本
         */
        public static byte[] encryptAES(byte[] content, byte[] key, byte[] iv) {
            Key secret = new SecretKeySpec(key, ALGORITHM);
            try {
                // 指定bouncycastle provider
                Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
                // 指定initialization vector
                AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);
                return cipher.doFinal(content);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException ignore) {
                Log.d("meee",Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getMethodName()+"()\n"
                        +ignore.getMessage());

            } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
                Log.d("meee",Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getMethodName()+"()\n"
                        +e.getMessage());

                e.printStackTrace();
            }
            return null;
        }

        /**
         * AES解密
         * @param content   内容
         * @param key       密钥
         * @param iv        初始化向量
         * @return          解密后的文本
         */
        public static byte[] decryptAES(byte[] content, byte[] key, byte[] iv) {
            Key secret = new SecretKeySpec(key, ALGORITHM);
            try {
                // 指定bouncycastle provider
                Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
                // 指定initialization vector
                AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);
                return cipher.doFinal(content);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException ignore) {
                Log.d("meee",Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getMethodName()+"()\n"
                        +ignore.getMessage());
            } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException
                    | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
                Log.d("meee",Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getMethodName()+"()\n"
                        +e.getMessage());
            }
            return null;
        }

        /**
         * AES加密 注意该方法minSdkVersion19
         * @param content   待加密内容
         * @param key       密钥
         * @param iv        初始化向量
         * @param aad       附加认证数据
         * @return          经过AES加密后的文本
         *
         * 注意该方法minSdkVersion19
         */
        public static byte[] encryptAES(byte[] content, byte[] key, byte[] iv, byte[] aad) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
                return null;

            Key secret = new SecretKeySpec(key, ALGORITHM);
            try {
                // 指定bouncycastle provider
                Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
                // 指定initialization vector
                AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);
                cipher.updateAAD(aad);
                return cipher.doFinal(content);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException ignore) {
            } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * AES解密 注意该方法minSdkVersion19
         * @param content   内容
         * @param key       密钥
         * @param iv        初始化向量
         * @param aad       附加认证数据
         * @return          解密后的文本
         */
        public static byte[] decryptAES(byte[] content, byte[] key, byte[] iv, byte[] aad) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
                return null;

            Key secret = new SecretKeySpec(key, ALGORITHM);
            try {
                // 指定bouncycastle provider
                Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
                // 指定initialization vector
                AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    cipher.updateAAD(aad);
                }
                return cipher.doFinal(content);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException ignore) {
            } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
