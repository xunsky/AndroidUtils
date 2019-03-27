package xunsky.utils.android_utils.encrypt;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Utils {
    public static String get(String str){
        MessageDigest messageDigest;
        String encodeStr = "";
        try{
            messageDigest=MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr=byte2Hex(messageDigest.digest());
        }catch (Exception ex){
            Log.e("meee",Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getMethodName()+"()\n"
                    +ex.getMessage());
        }
        return encodeStr;
    }

    private static String  byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp=null;
        for (int i = 0; i < bytes.length; i++) {
            temp=Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}
