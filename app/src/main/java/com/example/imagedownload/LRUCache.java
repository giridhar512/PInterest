package com.example.imagedownload;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache implements ICache {
    private final int mCacheSize;
    private final Map<String, String> mCache;

    public LRUCache(int cacheSize) {
        mCacheSize = cacheSize;
        mCache = new LinkedHashMap<>(cacheSize);
    }

    @Override
    public void store(String httpUrl, String cachePath) {
        if (get(httpUrl) == null) {
            if (mCache.size() == mCacheSize) {
                String firstKey = mCache.entrySet().iterator().next().getKey();
                mCache.remove(firstKey);
            }
            mCache.put(httpUrl, cachePath);
        }
    }

    @Override
    public String get(String httpUrl) {
        String cachePath = mCache.get(httpUrl);
        if (cachePath != null) {
            mCache.remove(httpUrl);
            mCache.put(httpUrl, cachePath);
        }
        return cachePath;
    }
}
