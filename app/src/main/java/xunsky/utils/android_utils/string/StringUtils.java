package xunsky.utils.android_utils.string;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 实现String与Base64String的转换
 * 实现Bitmap与Base64String的转换
 */
public class StringUtils {
    /**
     * string与base64的互相转换
     */
    public static String string2base64(String string){
        byte[] bytes = string.getBytes();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    public static String base642string(String base64string){
        byte[] bytes = Base64.decode(base64string, Base64.DEFAULT);
        return bytes.toString();
    }

    /**
     * bitmap与base64的互相转换
     */
    public static String bitmap2base64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                //如果有透明的部分,解码后该背景会变黑
                //有需要就将格式改为png
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public static Bitmap base642bitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length );
    }


}
