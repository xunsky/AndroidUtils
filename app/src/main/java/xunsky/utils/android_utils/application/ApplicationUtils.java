package xunsky.utils.android_utils.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class ApplicationUtils {
    /**
     * 判断目标应用是否安装
     */
    public static boolean isAppInstalled(Context context, String packagename ) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo( packagename, 0 );
        } catch( PackageManager.NameNotFoundException e ) {
            packageInfo = null;
        }
        if( packageInfo == null ) {
            return false;
        } else {
            return true;
        }
    }
}
