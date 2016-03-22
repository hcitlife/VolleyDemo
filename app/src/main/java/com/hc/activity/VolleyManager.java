package com.hc.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by HC on 2015-11-20-0020.
 */
public class VolleyManager {
    private static VolleyManager instance;
    private  RequestQueue queue;
    private  ImageLoader loader;


    public  RequestQueue getQueue() {
        return queue;
    }

    public  ImageLoader getLoader() {
        return loader;
    }

    private VolleyManager(Context context) {
        queue = Volley.newRequestQueue(context);
        ImageLoader.ImageCache cache = new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String s) {
                return cache.get(s);
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {
                cache.put(s, bitmap);
            }
        };
        loader = new ImageLoader(queue, cache);//使用LruCache实现缓存
    }

    public static VolleyManager getInstance(Context context) {
        if (context != null) {
            if (instance == null)
                instance = new VolleyManager(context);
        } else {
            throw new IllegalArgumentException("Context must be set");
        }
        return instance;
    }

}
