package xunsky.utils.android_utils.view;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import xunsky.utils.context_provider.ContextProvider;

public class ViewUtils {
    public static int dp2px(float dp) {
        final float scale = ContextProvider.get().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(float px) {
        final float scale = ContextProvider.get().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int sp2px(float spVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, ContextProvider.get().getResources().getDisplayMetrics());
    }

    public static float px2sp(Context context, float pxVal){
        return (pxVal / ContextProvider.get().getResources().getDisplayMetrics().scaledDensity);
    }

    public static final int getScreenWidth() {
        DisplayMetrics displayMetrics =
                ContextProvider.get().getResources().getDisplayMetrics();

        return displayMetrics.widthPixels;
    }

    public static final int getScreenHeight() {
        DisplayMetrics displayMetrics =
                ContextProvider.get().getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
}
