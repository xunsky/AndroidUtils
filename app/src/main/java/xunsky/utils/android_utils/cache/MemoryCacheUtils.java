package xunsky.utils.android_utils.cache;

import android.support.annotation.NonNull;

public final class MemoryCacheUtils {

    private static MemoryCache sDefaultCacheMemoryUtils;

    /**
     * Set the default instance of {@link MemoryCache}.
     *
     * @param cacheMemoryUtils The default instance of {@link MemoryCache}.
     */
    public static void setDefaultCacheMemoryUtils(final MemoryCache cacheMemoryUtils) {
        sDefaultCacheMemoryUtils = cacheMemoryUtils;
    }

    /**
     * Put bytes in cache.
     *
     * @param key   The key of cache.
     * @param value The value of cache.
     */
    public static void put(@NonNull final String key, final Object value) {
        put(key, value, getDefaultCacheMemoryUtils());
    }

    /**
     * Put bytes in cache.
     *
     * @param key      The key of cache.
     * @param value    The value of cache.
     * @param saveTime The save time of cache, in seconds.
     */
    public static void put(@NonNull final String key, final Object value, int saveTime) {
        put(key, value, saveTime, getDefaultCacheMemoryUtils());
    }

    /**
     * Return the value in cache.
     *
     * @param key The key of cache.
     * @param <T> The value type.
     * @return the value if cache exists or null otherwise
     */
    public static <T> T get(@NonNull final String key) {
        return get(key, getDefaultCacheMemoryUtils());
    }

    /**
     * Return the value in cache.
     *
     * @param key          The key of cache.
     * @param defaultValue The default value if the cache doesn't exist.
     * @param <T>          The value type.
     * @return the value if cache exists or defaultValue otherwise
     */
    public static <T> T get(@NonNull final String key, final T defaultValue) {
        return get(key, defaultValue, getDefaultCacheMemoryUtils());
    }

    /**
     * Return the count of cache.
     *
     * @return the count of cache
     */
    public static int getCacheCount() {
        return getCacheCount(getDefaultCacheMemoryUtils());
    }

    /**
     * Remove the cache by key.
     *
     * @param key The key of cache.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static Object remove(@NonNull final String key) {
        return remove(key, getDefaultCacheMemoryUtils());
    }

    /**
     * Clear all of the cache.
     */
    public static void clear() {
        clear(getDefaultCacheMemoryUtils());
    }

    ///////////////////////////////////////////////////////////////////////////
    // dividing line
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Put bytes in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param cacheMemoryUtils The instance of {@link MemoryCache}.
     */
    public static void put(@NonNull final String key,
                           final Object value,
                           @NonNull final MemoryCache cacheMemoryUtils) {
        cacheMemoryUtils.put(key, value);
    }

    /**
     * Put bytes in cache.
     *
     * @param key              The key of cache.
     * @param value            The value of cache.
     * @param saveTime         The save time of cache, in seconds.
     * @param cacheMemoryUtils The instance of {@link MemoryCache}.
     */
    public static void put(@NonNull final String key,
                           final Object value,
                           int saveTime,
                           @NonNull final MemoryCache cacheMemoryUtils) {
        cacheMemoryUtils.put(key, value, saveTime);
    }

    /**
     * Return the value in cache.
     *
     * @param key              The key of cache.
     * @param cacheMemoryUtils The instance of {@link MemoryCache}.
     * @param <T>              The value type.
     * @return the value if cache exists or null otherwise
     */
    public static <T> T get(@NonNull final String key, @NonNull final MemoryCache cacheMemoryUtils) {
        return cacheMemoryUtils.get(key);
    }

    /**
     * Return the value in cache.
     *
     * @param key              The key of cache.
     * @param defaultValue     The default value if the cache doesn't exist.
     * @param cacheMemoryUtils The instance of {@link MemoryCache}.
     * @param <T>              The value type.
     * @return the value if cache exists or defaultValue otherwise
     */
    public static <T> T get(@NonNull final String key,
                            final T defaultValue,
                            @NonNull final MemoryCache cacheMemoryUtils) {
        return cacheMemoryUtils.get(key, defaultValue);
    }

    /**
     * Return the count of cache.
     *
     * @param cacheMemoryUtils The instance of {@link MemoryCache}.
     * @return the count of cache
     */
    public static int getCacheCount(@NonNull final MemoryCache cacheMemoryUtils) {
        return cacheMemoryUtils.getCacheCount();
    }

    /**
     * Remove the cache by key.
     *
     * @param key              The key of cache.
     * @param cacheMemoryUtils The instance of {@link MemoryCache}.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static Object remove(@NonNull final String key, @NonNull final MemoryCache cacheMemoryUtils) {
        return cacheMemoryUtils.remove(key);
    }

    /**
     * Clear all of the cache.
     *
     * @param cacheMemoryUtils The instance of {@link MemoryCache}.
     */
    public static void clear(@NonNull final MemoryCache cacheMemoryUtils) {
        cacheMemoryUtils.clear();
    }

    private static MemoryCache getDefaultCacheMemoryUtils() {
        return sDefaultCacheMemoryUtils != null ? sDefaultCacheMemoryUtils : MemoryCache.getInstance();
    }
}
