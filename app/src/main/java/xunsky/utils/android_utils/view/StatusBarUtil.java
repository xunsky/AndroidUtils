package xunsky.utils.android_utils.view;



import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Provider;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import xunsky.utils.context_provider.ContextProvider;


public class StatusBarUtil {

    public static int screenWidth;
    public static int screenHeight;
    public static int navigationHeight = 0;

    private static DisplayMetrics mMetrics;
    public static final String HOME_CURRENT_TAB_POSITION = "HOME_CURRENT_TAB_POSITION";


    /**
     * @param useThemestatusBarColor   使用系统主题状态栏的颜色，不设置则为透明色,false透明状态栏,true#5#5#5
     * @param statusTextWhite 是否 为亮色调,否为暗色调
     */
    public static void setStatusBar(Activity activity, boolean useThemestatusBarColor, boolean statusTextWhite) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();

            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            decorView.setSystemUiVisibility(option);

            if (useThemestatusBarColor) {
                activity.getWindow().setStatusBarColor(Color.parseColor("#F5F5F5"));
            } else {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !statusTextWhite) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
    /**
     * 通过反射的方式获取状态栏高度
     */
    public static int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return ContextProvider.get().getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取底部导航栏高度
     */
    public static int getNavigationBarHeight() {
        Resources resources = ContextProvider.get().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        navigationHeight = resources.getDimensionPixelSize(resourceId);
        return navigationHeight;
    }

    /**
     * 判断是否存在底部导航栏
     */
    public static boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = ContextProvider.get().getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    /**
     * 设置透明状态栏
     */
    public static void setTransparent(Activity act){
        setStatusBar(act,false,false);
    }

    /**
     * 设置透明状态栏
     */
    public static void setTransparent(Activity act,boolean isTextWhite){
        setStatusBar(act,false,isTextWhite);
    }

    /**
     * 改变魅族的状态栏字体为黑色，要求FlyMe4以上
     */
    private static void processFlyMe(boolean isLightStatusBar, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        try {
            Class<?> instance = Class.forName("android.view.WindowManager$LayoutParams");
            int value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp);
            Field field = instance.getDeclaredField("meizuFlags");
            field.setAccessible(true);
            int origin = field.getInt(lp);
            if (isLightStatusBar) {
                field.set(lp, origin | value);
            } else {
                field.set(lp, (~value) & origin);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * 改变小米的状态栏字体颜色为黑色, 要求MIUI6以上  lightStatusBar为真时表示黑色字体
     */
    private static void processMIUI(boolean lightStatusBar, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags",int.class,int.class);
            extraFlagField.invoke(activity.getWindow(), lightStatusBar? darkModeFlag : 0, darkModeFlag);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    /**
     * 判断手机是否是小米
     * @return
     */
    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 判断手机是否是魅族
     * @return
     */
    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * 设置状态栏文字色值为深色调
     * @param useDart 是否使用深色调
     */
    public static void setStatusTextColor(boolean useDart, Activity activity) {
        if (isFlyme()) {
            processFlyMe(useDart, activity);
        } else if (isMIUI()) {
            processMIUI(useDart, activity);
        } else {
            if (useDart) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            activity.getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, navigationHeight);
        }
    }


    /**
     * 获取系统属性的封装类
     */
    public static class BuildProperties {
        private final Properties properties;
        private BuildProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }
        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }
        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }
        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }
        public String getProperty(final String name) {
            return properties.getProperty(name);
        }
        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }
        public boolean isEmpty() {
            return properties.isEmpty();
        }
        public Enumeration<Object> keys() {
            return properties.keys();
        }
        public Set<Object> keySet() {
            return properties.keySet();
        }
        public int size() {
            return properties.size();
        }
        public Collection<Object> values() {
            return properties.values();
        }
        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }
    }
}