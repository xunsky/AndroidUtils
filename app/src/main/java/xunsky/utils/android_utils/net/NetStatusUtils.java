package xunsky.utils.android_utils.net;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import xunsky.utils.context_provider.ContextProvider;


public class NetStatusUtils {
    /**
     * 注意需要权限:Manifest.permission.ACCESS_NETWORK_STATE
     */
    public static boolean isConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) ContextProvider.get()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {
            @SuppressLint("MissingPermission")
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否处于wifi环境
     * 注意需要权限:Manifest.permission.ACCESS_NETWORK_STATE
     */
    public static boolean isWifi() {
        Context context=ContextProvider.get();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null) {
            return info.isAvailable();
        }
        return false;
    }


    /**
     * 跳转至网络设置页面
     */
    public static void openNetworkSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
    public static void openNetworkSetting() {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ContextProvider.get().startActivity(intent);
    }

    /**
     * 获取网络状态
     */
    public static String GetNetworkType() {
        Context context=ContextProvider.get();
        String strNetworkType = "NONE";

        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }

    /**
     * 获取MAC地址
     */
    public static String getMAC() {
        Context context=ContextProvider.get();
        if (context == null) {
            return null;
        }
        WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi==null)
            return "";
        WifiInfo info = wifi.getConnectionInfo();
        if (info==null)
            return "";
        @SuppressLint("HardwareIds")
        String macAddress = info.getMacAddress();// 4C:AA:16:24:F5:43
        if (TextUtils.isEmpty(macAddress))
            macAddress = "";
        return macAddress;
    }

    /**
     * 获取基站编号
     * 需要权限:Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
     */
    public static int getCid(Context context) {
        int cid = 0;
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            @SuppressLint("MissingPermission")
            CellLocation celllocation = tm.getCellLocation();
            if (celllocation instanceof GsmCellLocation) {
                GsmCellLocation location = (GsmCellLocation) celllocation;
                if (location != null) {
                    cid = location.getCid();
                }
            } else if (celllocation instanceof CdmaCellLocation) {
                CdmaCellLocation location = (CdmaCellLocation) celllocation;
                if (location != null) {
                    cid = location.getBaseStationId();
                }
            }
        }
        return cid;
    }


    /**
     * 获取ip地址
     */
    public static String getIp() {
        Context context=ContextProvider.get();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
        }
        return "127.0.0.1";
    }

    /**
     * 获取运营商名称
     */
    public static String getOperateName() {
        Context context=ContextProvider.get();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm.getSimOperator();
        if (operator != null) {
            if ("46000".equals(operator) || "46002".equals(operator)) {
                return "中国移动";
            } else if ("46001".equals(operator)) {
                return "中国联通";
            } else if ("46003".equals(operator)) {
                return "中国电信";
            } else {
                return tm.getSimOperatorName();
            }
        }
        return null;
    }
}
