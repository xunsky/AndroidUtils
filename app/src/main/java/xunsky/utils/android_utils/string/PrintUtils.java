package xunsky.utils.android_utils.string;

import android.support.annotation.Nullable;

import java.util.List;

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
}
