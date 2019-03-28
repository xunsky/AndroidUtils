package xunsky.utils.android_utils.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import xunsky.utils.android_utils.encrypt.Md5Utils;
import xunsky.utils.context_provider.ContextProvider;

public class ApplicationUtils {
    /**
     * 通过签名获取其唯一字符串
     */
    public static String getSignature(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        StringBuilder sb = new StringBuilder();
        try {
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = pi.signatures;
            for (int i = 0; i < signatures.length; i++) {
                sb.append(signatures[i]);
            }
            String md5 = Md5Utils.getMD5(sb.toString());
            return md5;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 判断目标应用是否安装
     */
    public static boolean isAppInstalled(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断应用是否在前台
     */
    public static boolean isForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * sd卡是否安装
     */
    public static boolean isSdCardMounted() {
        boolean sdCardExist =
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            return true;
        }
        return false;
    }

    /**
     * 获取应用的version code
     */
    public static int getVersionCode() {
        try {
            PackageInfo pi = ContextProvider.get().getPackageManager().getPackageInfo(ContextProvider.get().getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取应用的version name
     */
    public static String getVersionName() {
        try {
            PackageManager manager = ContextProvider.get().getPackageManager();
            PackageInfo info = manager.getPackageInfo(ContextProvider.get().getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 设置屏幕的亮度 0-255
     */
    public static void setScreenBrightness(int paramInt, Activity activity) {
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        float f = paramInt / 255.0F;
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
    }

    /**
     * 设置是否自动调整亮度
     */
    public static void setScreenBrightnessAuto(boolean auto) {
        try {
            android.provider.Settings.System.putInt(ContextProvider.get().getContentResolver()
                    , android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE, auto?1:0);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    /**
     * 返回sd存储目录,可能为空
     */
    public static String getSdcardRoot(){
        if (isSdCardMounted()){
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }

    /**
     * 获取下载缓存目录
     */
    public static String getDownloadCachePath(){
        return Environment.getDownloadCacheDirectory().getPath();
    }

    /**
     * 打开输入法
     */
    public static void openKeybord(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }
    public static void openKeybord(Window window) {
        InputMethodManager imm = (InputMethodManager) window.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.showSoftInputFromInputMethod(window.getDecorView().getWindowToken(), InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭输入法
     */
    public static void closeKeybord(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    public static void closeKeybord(Window window) {
        InputMethodManager imm = (InputMethodManager) window.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);
    }
}
