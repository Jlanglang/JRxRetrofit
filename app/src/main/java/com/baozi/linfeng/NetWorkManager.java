package com.baozi.linfeng;


import android.app.Application;

import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.onExceptionListener;
import com.baozi.linfeng.location.retrofit.parse.IParse;
import com.baozi.linfeng.location.retrofit.RetrofitUtil;

import java.net.Proxy;
import java.util.HashSet;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 网络管理类
 */
public final class NetWorkManager {
    private static boolean isDebug = false;
    private static int DEFAULT_TIME_OUT = 30;
    private static int DEFAULT_RETRY = 5;

    private static onExceptionListener errorListener;
    /**
     * code状态码处理回调
     */
    private static APICallBack apiCallBack;

    private static Application mContext;

    public static HashSet<Interceptor> mInterceptors = new HashSet<>();

    public static HashSet<IParse> rxParseInfoSet = new HashSet<>();

    private static Proxy proxy; // 代理

    private static Function<OkHttpClient.Builder, OkHttpClient.Builder> flagMap;

    private NetWorkManager() {

    }

    /**
     * 初始化
     */
    public static void init(String baseUrl, Application context) {
        mContext = context;
        RetrofitUtil.init(baseUrl, context);
    }

    /**
     * 打开debug
     */
    public static void setDebug(boolean b) {
        isDebug = b;
    }

    /**
     * 打开debug
     */
    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDefaultTimeOut(int defaultTimeOut) {
        DEFAULT_TIME_OUT = defaultTimeOut;
    }

    public static int getDefaultTimeOut() {
        return DEFAULT_TIME_OUT;
    }

    public static void setDefaultRetry(int defaultRetry) {
        DEFAULT_RETRY = defaultRetry;
    }

    public static int getDefaultRetry() {
        return DEFAULT_RETRY;
    }

    public static void addInterceptor(Interceptor interceptor) {
        mInterceptors.add(interceptor);
    }

    /**
     * 注册 异常时提示消息
     *
     * @param msg 消息
     */
    public static void setExceptionListener(onExceptionListener msg) {
        errorListener = msg;
    }

    /**
     * 获取异常时提示消息
     *
     * @return 消息
     */
    public static onExceptionListener getExceptionListener() {
        return errorListener;
    }

    /**
     * 添加全局code状态处理
     *
     * @param callBack 回调
     * @type 保证唯一性
     */
    public static void setApiCallBack(APICallBack callBack) {
        apiCallBack = callBack;
    }

    /**
     * 获取状态处理回调
     *
     * @return APIExceptionCallBack回调接口
     */
    public static APICallBack getApiCallback() {
        return apiCallBack;
    }

    public static Application getContext() {
        return mContext;
    }


    public static Proxy getProxy() {
        return proxy;
    }


    public static Function<OkHttpClient.Builder, OkHttpClient.Builder> getFlagMap() {
        return flagMap;
    }

    public static void setFlagMap(Function<OkHttpClient.Builder, OkHttpClient.Builder> flagMap) {
        NetWorkManager.flagMap = flagMap;
    }
    public static HashSet<IParse> getParseInfo() {
        return rxParseInfoSet;
    }

    public static void addParseInfo(IParse parseInterceptor) {
        rxParseInfoSet.add(parseInterceptor);
    }
}
