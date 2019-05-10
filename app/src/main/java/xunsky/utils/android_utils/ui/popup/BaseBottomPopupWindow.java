package xunsky.utils.android_utils.ui.popup;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import xunsky.utils.android_utils.R;


public abstract class BaseBottomPopupWindow extends PopupWindow {
    private Activity act;
    public BaseBottomPopupWindow(Activity context, int layoutId) {
        this(context,layoutId,false,false);
    }

    public BaseBottomPopupWindow(final Activity context, int layoutId, boolean canTouchOutsize, boolean cancelable) {
        act =context;
        View rootView = LayoutInflater.from(context).inflate(layoutId, null);

        boolean b = rootView.getParent() == null;
        FrameLayout fl = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.base_bottom_dialog, null);
        fl.addView(rootView);
        if (rootView==null){
            throw new RuntimeException("can not inflate dialog layout");
        }
        initView(rootView);
        setContentView(fl);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);

        setAnimationStyle(R.style.dialog_style);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        setBackgroundDrawable(dw);
        backgroundAlpha(context, 0.8f);

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(context, 1f);
            }
        });
    }

    public abstract void initView(View root);

    public void show(){
        showAtLocation(act.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    public void backgroundAlpha(Activity act, float bgAlpha) {
        WindowManager.LayoutParams lp = act.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        act.getWindow().setAttributes(lp);
    }
}
