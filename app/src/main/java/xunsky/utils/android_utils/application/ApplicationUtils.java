package xunsky.utils.android_utils.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import xunsky.utils.context_provider.ContextProvider;

public class ApplicationUtils {
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
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = ContextProvider.get().getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
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
}
