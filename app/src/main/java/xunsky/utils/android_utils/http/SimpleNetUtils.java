package xunsky.utils.android_utils.http;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleNetUtils {
    private static ExecutorService sCacheExector = new ThreadPoolExecutor(4, 10, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    private static Handler sHandler = new Handler();

    public interface OnNet {
        void onSuccessed(String data);
        void onFailed(String data);
    }


    public static void get(final String url, final Params params, final OnNet callback) {
        get(url, params.commit(), callback);
    }

    public static void get(final String url, final HashMap<String, String> params, final OnNet callback) {
        final String reallyUrl = getUrl(url, params);;
        sCacheExector.submit(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                ByteArrayOutputStream baos = null;
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(reallyUrl)
                            .openConnection();
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");

                    int status = conn.getResponseCode();
                    if (status == 200) {
                        is = conn.getInputStream();
                        baos = new ByteArrayOutputStream();
                        int len = -1;
                        byte[] buf = new byte[128];
                        while ((len = is.read(buf)) != -1) {
                            baos.write(buf, 0, len);
                        }
                        baos.flush();
                        final String data = baos.toString();
                        sHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccessed(data);
                            }
                        });
                    } else {
                        callback.onFailed("请求错误:" + status + " :" + conn.getResponseMessage());
                    }
                } catch (Exception e) {
                    callback.onFailed("请求错误:" + e != null ? e.getMessage() : "");
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (baos != null) {
                            baos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void post(final String url, final Params params, final OnNet callback){
        post(url,params.commit(),callback);
    }
    public static void post(final String url, final HashMap<String,String> params, final OnNet callback){
        final String reallyUrl = getUrl(url, params);
        sCacheExector.submit(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                ByteArrayOutputStream baos = null;
                try {
                    HttpURLConnection conn = (HttpURLConnection) new URL(reallyUrl)
                            .openConnection();

                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setUseCaches(false);

                    conn.setDoOutput(true);//必须
                    conn.setDoInput(true);//必须
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(5000);



                    int status = conn.getResponseCode();
                    if (status == 200) {

                        is = conn.getInputStream();
                        baos = new ByteArrayOutputStream();
                        int len = -1;
                        byte[] buf = new byte[128];
                        while ((len = is.read(buf)) != -1) {
                            baos.write(buf, 0, len);
                        }
                        baos.flush();
                        final String data = baos.toString();
                        sHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccessed(data);
                            }
                        });
                    } else {
                        callback.onFailed("请求错误:" + status + " :" + conn.getResponseMessage());
                    }
                } catch (Exception e) {
                    callback.onFailed("请求错误:" + e != null ? e.getMessage() : "");
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (baos != null) {
                            baos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接  
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性  
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行  
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流  
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数  
                out.print(param);
                // flush输出流的缓冲  
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应  
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流  
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static class Params {
        HashMap<String, String> params;

        public static Params newInstance() {
            Params params = new Params(new HashMap<String, String>());
            return params;
        }

        public static Params newInstance(HashMap<String, String> map) {
            if (map == null)
                throw new RuntimeException("params must not be null = =!");
            Params params = new Params(map);
            return params;
        }

        private Params(HashMap<String, String> map) {
            params = map;
        }

        public Params add(String key, String value) {
            params.put(key, value);
            return this;
        }

        public HashMap<String, String> commit() {
            return params != null ? params : new HashMap<String, String>();
        }
    }

    /**
     * 拼接查询参数的工具方法
     */
    private static String getUrl(String url, HashMap map) {
        if (map == null || map.size() == 0) {
            return url;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?");

        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append(key);
            sb.append("=");
            sb.append(value);
            sb.append("&");
        }
        url = sb.substring(0, sb.length() - 1);
        return url;
    }
    private static String getUrl(HashMap map) {
        StringBuilder sb = new StringBuilder();
        sb.append("?");

        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            sb.append(key);
            sb.append("=");
            sb.append(value);
            sb.append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
