package com.hc.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //        getString1();
        //                getString2();
        //        getJson();
        //                getImage1();
        //                getImage2();
        //                getImage3();
        //                getImage4();
//        getXML();
        getGson();
    }

    private void getString1() {
        String url = "http://www.baidu.com";
        VolleyManager manager = VolleyManager.getInstance(this);
        RequestQueue queue = manager.getQueue();
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("请求失败");
            }
        });
        queue.add(request);
    }

    private void getString2() {
        String url = "http://192.168.20.60:8080/WebServer/login.jsp";
        VolleyManager manager = VolleyManager.getInstance(this);
        RequestQueue queue = manager.getQueue();
        Response.Listener<String> successListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, url, successListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("name", "hc");
                map.put("pass", "hc");
                return map;
            }
        };
        queue.add(request);
    }

    private void getJson() {
        String url = "http://www.weather.com.cn/adat/cityinfo/101010100.html";
        VolleyManager manager = VolleyManager.getInstance(this);
        RequestQueue queue = manager.getQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
        queue.start();
    }

    private void getImage1() {
        String url = "http://iotekimg.b0.upaiyun.com/iotek2/goods_v2/android_5_150604162255.jpg";
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        ImageRequest imgRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 300, 200, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errr) {
                imageView.setImageResource(R.drawable.moren);
            }
        });
        VolleyManager manager = VolleyManager.getInstance(this);
        RequestQueue queue = manager.getQueue();
        queue.add(imgRequest);
    }

    ////////////////////////////////////////
    private void getImage2() {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        String url = "http://iotekimg.b0.upaiyun.com/iotek2/goods_v2/android_1_150604162048.jpg";

        VolleyManager manager = VolleyManager.getInstance(this);
        RequestQueue queue = manager.getQueue();
        ImageLoader loader = manager.getLoader();

        ImageLoader.ImageListener listener = loader.getImageListener(imageView, R.drawable.moren, R.drawable.error);

        loader.get(url, listener);//加载图片
    }
    ////////////////////////////////

    private class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> cache;

        public BitmapCache() {
            int maxSize = 8 * 1024 * 1024;//设置缓存图片的大小
            cache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String s) {
            return cache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            cache.put(s, bitmap);
        }
    }

    //    使用ImageLoader来进行网络图片的加载.
    //    ImageLoader的内部使用ImageRequest来实现，它的构造器可以传入一个ImageCache缓存形参,实现了图片缓存的功能,同时还可以过滤重复链接，避免重复发送请求。
    private void getImage3() {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        String url = "http://iotekimg.b0.upaiyun.com/iotek2/goods_v2/android_5_150604162255.jpg";

        VolleyManager manager = VolleyManager.getInstance(this);
        RequestQueue queue = manager.getQueue();
        ImageLoader loader = new ImageLoader(queue, new BitmapCache()); //自定义cache

        //        使用ImageLoader.getImageListener()方法创建一个ImageListener实例后,在imageLoader.get()方法中加入此监听器和图片的url,即可加载网络图片
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.moren, R.drawable.error);
        loader.get(url, listener);

        //指定图片允许的最大宽度和高度
        loader.get(url, listener, 200, 200);
    }

    /////////////////////
    private void getImage4() {
        String url = "http://iotekimg.b0.upaiyun.com/iotek2/goods_v2/android_5_150604162255.jpg";
        VolleyManager manager = VolleyManager.getInstance(this);
        RequestQueue queue = manager.getQueue();
        ImageLoader loader = manager.getLoader();
        NetworkImageView imageView = (NetworkImageView) findViewById(R.id.networkImageView);
        imageView.setTag("url");
        imageView.setImageUrl(url, loader);
    }

    ///////////////////////////////////////////////////////////////////////////
    private class XMLRequest extends Request<XmlPullParser> {

        private Response.Listener<XmlPullParser> listener;

        public XMLRequest(int method, String url, Response.Listener<XmlPullParser> listener, Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            this.listener = listener;
        }

        public XMLRequest(String url, Response.Listener<XmlPullParser> listener, Response.ErrorListener errorListener) {
            this(Method.GET, url, listener, errorListener);
        }

        @Override
        protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
            try {
                String xmlStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(new StringReader(xmlStr));
                return Response.success(parser, HttpHeaderParser.parseCacheHeaders(response));
            } catch (Exception e) {
                e.printStackTrace();
                return Response.error(new ParseError());
            }
        }

        @Override
        protected void deliverResponse(XmlPullParser xmlPullParser) {
            listener.onResponse(xmlPullParser);
        }
    }

    private void getXML() {
        String url = "http://flash.weather.com.cn/wmaps/xml/china.xml";
        VolleyManager manager = VolleyManager.getInstance(this);
        RequestQueue queue = manager.getQueue();
        XMLRequest request = new XMLRequest(url, new Response.Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser parser) {
                try {
                    int eventType = parser.getEventType();
                    while (eventType != parser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            String tag = parser.getName();
                            if ("city".equals(tag)) {
                                String cityName = parser.getAttributeValue(2);
                                String state = parser.getAttributeValue(null, "stateDetailed");
                                Toast.makeText(MainActivity.this, cityName + "  " + state, Toast.LENGTH_SHORT).show();
                            }
                        }
                        eventType = parser.next();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "request failed", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    private class GsonRequest<T> extends Request<T> {

        private final Response.Listener<T> listener;

        private Gson gson;

        private Class<T> clazz;

        public GsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
                           Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            gson = new Gson();
            this.clazz = clazz;
            this.listener = listener;
        }

        public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener,
                           Response.ErrorListener errorListener) {
            this(Method.GET, url, clazz, listener, errorListener);
        }

        @Override
        protected Response<T> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                return Response.success(gson.fromJson(jsonString, clazz),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            }
        }

        @Override
        protected void deliverResponse(T response) {
            listener.onResponse(response);
        }

    }

    private void getGson() {
        String url = "http://www.weather.com.cn/adat/sk/101010100.html";
        VolleyManager manager = VolleyManager.getInstance(this);
        RequestQueue queen = manager.getQueue();

        GsonRequest<Weather> request = new GsonRequest<Weather>(url, Weather.class, new Response.Listener<Weather>() {
            @Override
            public void onResponse(Weather weather) {
                Weather.Weatherinfo weatherInfo = weather.getWeatherinfo();
                Toast.makeText(MainActivity.this, weatherInfo.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "request failed", Toast.LENGTH_SHORT).show();
            }
        });

        queen.add(request);

    }


}
