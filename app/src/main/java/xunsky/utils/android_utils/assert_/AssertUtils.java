package xunsky.utils.android_utils.assert_;

import android.util.Log;

public class AssertUtils {
    public static void makeSureForArray(Object... objs){
        for (Object obj:objs){
            boolean flag=false;

            if (obj instanceof Boolean && obj !=null){
                flag=((Boolean) obj).booleanValue();
            }else{
                flag=(obj!=null);
            }

            if (!flag){
                String message="assert fail on: ("+Thread.currentThread().getStackTrace()[3].getFileName()+":"+Thread.currentThread().getStackTrace()[3].getLineNumber()+")";
                throw new RuntimeException(message);
            }
        }
    }


    public static  void makeSure(Object obj){
        boolean flag=false;

        if (obj instanceof Boolean && obj !=null){
            flag=((Boolean) obj).booleanValue();
        }else{
            flag=(obj!=null);
        }

        if (!flag){
            String message="assert fail on: ("+Thread.currentThread().getStackTrace()[3].getFileName()+":"+Thread.currentThread().getStackTrace()[3].getLineNumber()+")";
            throw new RuntimeException(message);
        }
    }

    public static void makeSure(boolean flag){
        if (flag)
            return;
        String message="assert fail on: ("+Thread.currentThread().getStackTrace()[3].getFileName()+":"+Thread.currentThread().getStackTrace()[3].getLineNumber()+")";
        throw new RuntimeException(message);
    }
}
