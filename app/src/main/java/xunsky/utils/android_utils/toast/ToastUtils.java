package xunsky.utils.android_utils.toast;

import android.view.Gravity;
import android.widget.Toast;

import xunsky.utils.context_provider.ContextProvider;

public class ToastUtils {
    public static Toast sToast;
    public static void toast(String message){
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
    }
}
