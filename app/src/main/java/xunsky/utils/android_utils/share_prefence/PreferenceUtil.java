package xunsky.utils.android_utils.share_prefence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import xunsky.utils.context_provider.ContextProvider;


public final class PreferenceUtil {
    public  static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(ContextProvider.get());
    }

    /**
     * 清空所有数据
     */
    public  static void reset(final Context context) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.clear();
        edit.apply();
    }

    /**
     * 移除指定key的数据
     */
    public  static void remove(String... keys) {
        if (keys != null) {
            SharedPreferences sharedPreferences = getPreferences();
            Editor editor = sharedPreferences.edit();
            for (String key : keys) {
                editor.remove(key);
            }
            editor.apply();
        }
    }

    /**
     * boolean
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = getPreferences();
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public static boolean getBoolean(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    /**
     * String
     */
    public  static boolean hasString(String key) {
        SharedPreferences sharedPreferences = getPreferences();
        return sharedPreferences.contains(key);
    }
    public  static void putString(String key, String value) {
        SharedPreferences sharedPreferences = getPreferences();
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public  static String getString(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    /**
     * long
     */
    public  static void putLong(String key, long value) {
        SharedPreferences sharedPreferences = getPreferences();
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }
    public  static long getLong(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    /**
     * float
     */
    public  static void putFloat(String key, float value) {
        SharedPreferences sharedPreferences = getPreferences();
        Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }
    public  static float getFloat(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    /**
     * int
     */
    public  static void putInt(String key, int value) {
        SharedPreferences sharedPreferences = getPreferences();
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
    public  static int getInt(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }
}
