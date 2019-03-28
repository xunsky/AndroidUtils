package xunsky.utils.android_utils.json;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static Gson sGson=new GsonBuilder().serializeNulls().create();
    public static String toJson(Object obj){
        return sGson.toJson(obj);
    }

    public static <T> T parseJson(String json,Class<T> clazz){
        return sGson.fromJson(json,clazz);
    }

    public static <T> List<T> parseJsonList(String json, Class<T> clazz){
        //如下处理会在get(position)时 com.google.gson.internal.LinkedTreeMap cannot be cast to
        //真实类型为linkedTreeMap 无法get
        ArrayList<T> list = new ArrayList<>();
        JsonArray arry = new JsonParser().parse(json).getAsJsonArray();
        for (JsonElement jsonElement : arry) {
            list.add(sGson.fromJson(jsonElement, clazz));
        }
        for (T t: list) {
            Log.d("meee",Thread.currentThread().getStackTrace()[2].getClassName()+"."+Thread.currentThread().getStackTrace()[2].getLineNumber()+"\n"
                    +t.getClass());
        }
        return list;
    }
}
