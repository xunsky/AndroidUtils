package xunsky.utils.android_utils.string;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintUtils {
    /**
     * 输出当前堆栈
     */
    public static String getStack(@Nullable Exception e) {
        if (e == null) {
            e = new RuntimeException();
        }
        StackTraceElement[] stacks = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < stacks.length; i++) {
            StackTraceElement stack = stacks[i];
            sb.append(stack.getClassName())
                    .append(".")
                    .append(stack.getMethodName())
                    .append("()")
                    .append(" --> ")
                    .append("(").append(stack.getFileName()).append(":").append(stack.getLineNumber()).append(")")
                    .append("\n");
        }
        return sb.toString();
    }

    /**
     * 输出当前的文件的位置
     */
    public static String getFileLocation() {
        Exception e = new RuntimeException();
        StackTraceElement[] stacks = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        StackTraceElement stack = stacks[1];
        sb.append(stack.getClassName())
                .append(".")
                .append(stack.getMethodName())
                .append("()")
                .append(" --> ")
                .append("(").append(stack.getFileName()).append(":").append(stack.getLineNumber()).append(")")
                .append("\n");
        return sb.toString();
    }

    /**
     * 格式化list,方便打印日志
     */
    public static String printList(List list){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString()).append(",\n");
        }
        return sb.toString();
    }

    /**
     * 格式化map,方便打印日志
     */
    public static String printMap(Map map){
        return map.toString();
    }

    /**
     * 打印Cursor
     */
    public static String printCursor(Cursor cursor){
        List<HashMap<String,Object>> datas = new ArrayList<>();
        if (cursor==null)
            return printList(datas);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToNext()){
            String[] columnNames = cursor.getColumnNames();
            HashMap<String,Object> map = new HashMap<>();

            for (int i = 0; i < columnNames.length; i++) {
                String columnName = columnNames[i];
                int columnIndex = cursor.getColumnIndex(columnName);
                int type = cursor.getType(columnIndex);

                switch (type){
                    case Cursor.FIELD_TYPE_NULL:
                        map.put(columnName,"null");
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        int n = cursor.getInt(columnIndex);
                        map.put(columnName,n);
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        float f = cursor.getFloat(columnIndex);
                        map.put(columnName,f);
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        String str = cursor.getString(columnIndex);
                        map.put(columnName,str);
                        break;
                    case Cursor.FIELD_TYPE_BLOB:
                        byte[] blob = cursor.getBlob(columnIndex);
                        map.put(columnName,blob);
                        break;
                }
            }
            datas.add(map);
        }
        return printList(datas);
    }

    public static void logLong(String str){
        if (str==null)
            return;

        List<String> list = new ArrayList<String>();

        int logMax= 887;
        float v = (str.length() * 1.0f) / logMax;
        double size = Math.ceil(v);

        for (int index = 0; index < size; index++) {
            String childStr = substring(str, index * logMax,
                    (index + 1) * logMax);
            if (childStr!=null)
                list.add(childStr);
        }
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (i==0){
                Log.d("meee","("+Thread.currentThread().getStackTrace()[3].getFileName()+":"+Thread.currentThread().getStackTrace()[3].getLineNumber()+")\n"
                        +s);
            }else{
                Log.d("meee", s);
            }
        }
    }

    private static String substring(String str, int f, int t) {
        if (f > str.length())
            return null;
        if (t > str.length()) {
            return str.substring(f, str.length());
        } else {
            return str.substring(f, t);
        }
    }
}
