package com.baozi.linfeng.location.retrofit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.baozi.linfeng.factory.SSLSocketClient;
import com.linfeng.rx_retrofit_network.BuildConfig;
import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.converter.GsonConverterFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.Cache;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Sunflower on 2015/11/4.
 */
public class RetrofitUtil {


    /**
     * 服务器地址
     */
    private static String API_HOST;
    private static Application mContext;
    private static final HashMap<Class, Object> apis = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getApi(Class<T> c) {
        Object o = apis.get(c);
        if (null == o) {
            o = getInstance().create(c);
            apis.put(c, o);
        }
        return (T) o;
    }

    public static synchronized void init(String baseUrl, Application context) {
        if (TextUtils.isEmpty(baseUrl)) {
            return;
        }
        mContext = context;
        API_HOST = baseUrl;
        Instance.retrofit = Instance.getRetrofit();
        apis.clear();
    }

    public static Retrofit getInstance() {
        return Instance.retrofit;
    }

    private static class Instance {
        private static Retrofit retrofit = getRetrofit();

        private static Retrofit getRetrofit() {
            OkHttpClient.Builder client = new OkHttpClient.Builder()
                    //拦截并设置缓存
                    .addNetworkInterceptor(new GetCacheInterceptor())
                    //拦截并设置缓存
                    .addInterceptor(new GetCacheInterceptor());
            if (mContext != null) {
                client.cache(new Cache(mContext.getCacheDir(), 10240 * 1024));
            }
            // 设置代理
            if (NetWorkManager.getProxy() != null) {
                client.proxy(NetWorkManager.getProxy());
            }

            for (Interceptor i : NetWorkManager.mInterceptors) {
                client.addInterceptor(i);
            }
            if (NetWorkManager.isDebug) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                client.addInterceptor(interceptor);
            }
            try {
                client = NetWorkManager.getFlagMap().apply(client);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Retrofit.Builder()
                    .client(client.build())
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    /**
     * 此方法获取的bitmap为原始大小,图片文件过大可能造成oom
     *
     * @param images 图片集合
     */
    public static HashMap<String, RequestBody> creatRequestBodyImagesFiles(List<String> images) {
        if (images == null) {
            return null;
        }
        HashMap<String, RequestBody> photoRequestMap = new HashMap<>();
        int size = images.size();
        for (int i = 0; i < size; i++) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeFile(images.get(i));
            //转化为二进制流数组
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            photoRequestMap.put("file" + i + "\";filename=\"" +
                    System.currentTimeMillis(), RequestBody.create(MediaType.parse("multipart/form-data"), byteArray));
        }
        return photoRequestMap;
    }

    /**
     * 建议调用此方法前,先将bitmap压缩.
     *
     * @param images 图片集合
     */
    public static HashMap<String, RequestBody> creatRequestBodyBitmap(List<Bitmap> images) {
        if (images == null) {
            return null;
        }
        HashMap<String, RequestBody> photoRequestMap = new HashMap<>();
        int size = images.size();
        for (int i = 0; i < size; i++) {
            Bitmap bitmap = images.get(i);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //转化为二进制流数组
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            photoRequestMap.put("file" + i + "\";filename=\"" +
                    System.currentTimeMillis(), RequestBody.create(MediaType.parse("multipart/form-data"), byteArray));
        }
        return photoRequestMap;
    }

    private RequestBody buildMultipartFormRequestBody(List<File> files, String filesKey, HashMap<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        MultipartBody.Builder builder = new MultipartBody.Builder();
        Set<String> strings = params.keySet();
        for (String key : strings) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                    RequestBody.create(null, params.get(key)));
        }
        if (files == null) {
            files = new ArrayList<>();
        }
        int size = files.size();
        if (size == 0) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + filesKey + "\""),
                    RequestBody.create(null, "[]"));
        }
        for (int i = 0; i < size; i++) {
            //TODO 根据文件名设置contentType
            builder.addPart(Headers.of("Content-Disposition",
                    "form-data; name=\"" + filesKey + "\"; fileName=\"" + System.currentTimeMillis() + "\""),
                    RequestBody.create(MediaType.parse("multipart/form-data"), files.get(i)));
        }
        return builder.build();

    }

    /**
     * 判断网络是否打开
     *
     * @return 是否打开网络
     */
    public static boolean isOpenInternet(Context context) {
        if (context == null) return false;
        ConnectivityManager con = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        if (con != null) {
            boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
            boolean intenter = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
            return wifi || intenter;
        }
        return false;
    }
}
