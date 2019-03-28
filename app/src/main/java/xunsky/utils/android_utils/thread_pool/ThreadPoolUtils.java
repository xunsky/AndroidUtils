package xunsky.utils.android_utils.thread_pool;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by junx on 2017/12/29.
 */

public class ThreadPoolUtils {
    private static Handler mHandler;
    private static ExecutorService mCacheThreadPool;
    private static ExecutorService mFixedThreadPool;
    private static ScheduledExecutorService mScheduledThreadPool;
    private static ExecutorService mSingleThreadPool;
    public static Handler getHandler(){
        if (mHandler==null){
            synchronized (ThreadPoolUtils.class){
                if (mHandler==null){
                    mHandler=new Handler(Looper.getMainLooper());
                }
            }
        }
        return mHandler;
    }

    /**
     * post一个任务到主线程
     */
    public static void post(Runnable runnable){
        getHandler().post(runnable);
    }
    public static void postDelay(Runnable runnable,long delayTime){
        getHandler().postDelayed(runnable,delayTime);
    }

    /*
    * 线程数量不固定的线程池.
    * 没有非核心线程,最大线程数量为Integer.MAX_VALUE.
    * 所以当线程池中的线程都处于活动状态的时候,线程池会创建新的线程来处理新任务,否则使用空闲的线程来处理新任务.
    * 有超时机制,超时时间为60秒.
    * public ThreadPoolExecutor(orePoolSize=0,maximumPoolSize=Integer.MAX_VALUE,keepAliveTime=60L
    *,TimeUnit.SECONDS,new SynchronousQueue<Runnable>)
    * */

    public static ExecutorService getCacheThreadPool(){
        if (mCacheThreadPool==null){
            synchronized (ThreadPoolUtils.class){
                if (mCacheThreadPool==null){
                    mCacheThreadPool=Executors.newCachedThreadPool();
                }
            }
        }
        return mCacheThreadPool;
    }
    public static Future<?> startCacheTask(Runnable runnable){
        return getCacheThreadPool().submit(runnable);
    }

    /**
     * 是一种线程数量固定的线程池,当线程处于空闲状态时不会被回收除非线程池被回收.
     * 只有核心线程数,所以能够更快的响应外界的请求.
     */
    public static ExecutorService getFixedThreadPool(){
        if (mFixedThreadPool==null){
            synchronized (ThreadPoolUtils.class){
                if (mFixedThreadPool==null){
                    mFixedThreadPool=Executors.newFixedThreadPool(4);
                }
            }
        }
        return mFixedThreadPool;
    }
    public static Future<?> startFixedTask(Runnable runnable){
        return getFixedThreadPool().submit(runnable);
    }

    /**
     * 是一种核心线程数量固定但非核心线程没有限制的线程,非核心线程闲置时会立即回收.
     * 主要用于执行定时任务和具有固定周期的重复任务.
     * public ThreadPoolExecutor(corePoolSize=cpucores,maximumPoolSize=Integer.MAX_VALUE,keepAliveTime=0
     * ,NANOSECONDS,new DelayedWorkQueue<Runnable>)
     */
    public static ScheduledExecutorService getScheduledThreadPool(){
        if (mScheduledThreadPool ==null){
            synchronized (ThreadPoolUtils.class){
                if (mScheduledThreadPool ==null){
                    mScheduledThreadPool =Executors.newScheduledThreadPool(4);
                }
            }
        }
        return mScheduledThreadPool;
    }
    public static ScheduledFuture<?> startScheduledTask(Runnable runnable, long millis){
        return getScheduledThreadPool().schedule(runnable,millis,TimeUnit.MILLISECONDS);
    }
    public static ScheduledFuture<?> startLoopTask(Runnable runnable, long millis){
        return getScheduledThreadPool().scheduleAtFixedRate(runnable,0,millis,TimeUnit.MILLISECONDS);
    }

    /**
     * 内部只有一个核心线程没有非核心线程,确保所有的任务都在一个线程中按顺序执行.
     * 统一所有外界任务到一个线程中,让这些任务不要处理线程同步的问题.
     * public ThreadPoolExecutor(corePoolSize=1,maximumPoolSize=1,keepAliveTime=0L
     * ,TimeUnit.MILLISECONDS,new LinkedBolockingQueue<Runnable>)
     */
    public static ExecutorService getSingleThreadPool(){
        if (mSingleThreadPool==null){
            synchronized (ThreadPoolUtils.class){
                if (mSingleThreadPool==null){
                    mSingleThreadPool=Executors.newSingleThreadExecutor();
                }
            }
        }
        return mSingleThreadPool;
    }
    public static Future<?> startSingleThreadTask(Runnable runnable){
        return getSingleThreadPool().submit(runnable);
    }
}
