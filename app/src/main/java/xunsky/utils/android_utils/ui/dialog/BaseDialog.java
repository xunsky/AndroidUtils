package xunsky.utils.android_utils.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import xunsky.utils.android_utils.R;


public abstract class BaseDialog extends Dialog {
    public BaseDialog(Context context, int layoutId) {
        this(context,layoutId,false,false);
    }

    public BaseDialog(Context context, int layoutId, boolean canTouchOutsize, boolean cancelable) {
        super(context, R.style.dialog);
        View rootView = getLayoutInflater().inflate(layoutId, null);
        if (rootView==null){
            throw new RuntimeException("can not inflate dialog layout");
        }
        initView(rootView);
        setContentView(rootView);
        Window window = getWindow();
        if (window!=null){
            window.getAttributes().gravity= Gravity.CENTER;
        }
        setCanceledOnTouchOutside(canTouchOutsize);
        setCancelable(cancelable);
    }
    public abstract void initView(View root);
}
