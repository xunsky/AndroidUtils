package xunsky.utils.android_utils.log;

import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xunsky.utils.android_utils.file.FileUtils;
import xunsky.utils.android_utils.string.TimeFormatUtils;
import xunsky.utils.context_provider.ContextProvider;

public class LogUtils {
    public static String LOG_DIR = new File(ContextProvider.get().getCacheDir(),"log").getAbsolutePath();

    public static SimpleDateFormat sDayFormatter=new SimpleDateFormat("yyyy-MM-dd");

    public static void log2File(String logMessage){
        String date = TimeFormatUtils.millis2String(System.currentTimeMillis());
        String log = date+" (" + Thread.currentThread().getStackTrace()[3].getFileName() + ":" + Thread.currentThread().getStackTrace()[3].getLineNumber() + ")\n\t\t"
                + logMessage+"\n";
//        Log.d("meee", log);

        File logFile = getTodayLogFile();
        FileUtils.writeFileFromString(logFile,log,true);
    }

    public static File getTodayLogFile(){
        File file = new File(LOG_DIR, sDayFormatter.format(new Date()));
        if (!file.exists()){
        }

        return file;
    }

    public static List<File> getLogFiles(){
        File dir = new File(LOG_DIR);
        List<File> logFiles=new ArrayList<>();
        if (dir.exists()&&dir.isDirectory()){
            File[] files = dir.listFiles();
            for(File file:files){
                if (file.exists()&&file.isFile()){
                    logFiles.add(file);
                }
            }
        }
        return logFiles;
    }

    //该日志文件是几天之前的
    public static long pastDay(File logFile){
        try{
            String name = logFile.getName();

            Date date = LogUtils.sDayFormatter.parse(name);

            Date today = new Date();
            long diff = today.getTime() - date.getTime();

            return diff/(1000*60*60*24);
        }catch (Exception ex){
            return -1;
        }
    }
}
