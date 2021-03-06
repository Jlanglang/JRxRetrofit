package com.baozi.linfeng.location.retrofit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.converter.GsonConverterFactory;

import java.util.HashMap;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
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
                    .addInterceptor(new GetCacheInterceptor())
                    .cache(new Cache(mContext.getCacheDir(), 10240 * 1024));
            // 设置代理
            if (NetWorkManager.getProxy() != null) {
                client.proxy(NetWorkManager.getProxy());
            }

            for (Interceptor i : NetWorkManager.mInterceptors) {
                client.addInterceptor(i);
            }
            if (NetWorkManager.isDebug()) {
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
