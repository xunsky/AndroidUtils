package xunsky.utils.android_utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
    private static Gson sGson=new GsonBuilder().serializeNulls().create();
    public static String toJson(Object obj){
        return sGson.toJson(obj);
    }

    public static <T> T parseJson(String json,Class<T> clazz){
        return sGson.fromJson(json,clazz);
    }
}
