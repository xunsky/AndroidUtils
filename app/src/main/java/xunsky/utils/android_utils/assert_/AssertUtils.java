package xunsky.utils.android_utils.assert_;

import android.util.Log;

public class AssertUtils {
    public static  void makeSure(Object obj){
        if (obj instanceof Boolean && obj !=null){
            makeSure(((Boolean) obj).booleanValue());
            return;
        }
        makeSure(obj!=null);

    }

    public static void makeSure(boolean flag){
        if (flag)
            return;
        String message="assert fail on: ("+Thread.currentThread().getStackTrace()[3].getFileName()+":"+Thread.currentThread().getStackTrace()[3].getLineNumber()+")";
        throw new RuntimeException(message);
    }
}
