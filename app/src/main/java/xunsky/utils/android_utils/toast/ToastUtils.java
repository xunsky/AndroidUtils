package xunsky.utils.android_utils.toast;

import android.app.Application;
import android.view.Gravity;
import android.widget.Toast;

import xunsky.utils.context_provider.ContextProvider;

public class ToastUtils {
    private static boolean isSupport=false;
    /*
    * 部分手机关闭通知后无法显示通知
    * 开启支持让其可以显示toast
    * */
    private static void useSupport(Application app){
        com.hjq.toast.ToastUtils.init(app);
        isSupport=true;
    }
    public static Toast sToast;
    public static void toast(String message){
        if (!isSupport){
            if (sToast==null){
                synchronized (ToastUtils.class){
                    if (sToast==null){
                        sToast=Toast.makeText(ContextProvider.get(), "", Toast.LENGTH_LONG);
//                    sToast.setGravity(Gravity.CENTER, 0,0);
                    }
                }
            }
            sToast.setText(message);
            sToast.show();
        }else{
            com.hjq.toast.ToastUtils.show(message);
        }
    }
}
