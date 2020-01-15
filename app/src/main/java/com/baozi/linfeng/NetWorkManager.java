package com.baozi.linfeng;


import android.app.Application;

import com.baozi.linfeng.factory.EncodeDecodeKey;
import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.onExceptionListener;
import com.baozi.linfeng.location.retrofit.RetrofitUtil;
import com.baozi.linfeng.location.retrofit.ParseInfo;

import java.net.Proxy;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 网络管理类
 */
public final class NetWorkManager {
    public static boolean isDebug = false;
    private static int DEFAULT_TIME_OUT = 30;
    private static int DEFAULT_RETRY = 5;

    private static onExceptionListener errorListener;
    /**
     * code状态码处理回调
     */
    private static APICallBack apiExceptionCallBacks;
    /**
     * 私钥
     */
    private static String privateKey;
    /**
     * 公钥
     */
    private static String publicKey;

    private static Application mContext;

    public static HashSet<Interceptor> mInterceptors = new HashSet<>();

    public static HashSet<ParseInfo> rxParseInfoSet = new HashSet<>();

    private static Proxy proxy; // 代理


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
    public static void isDebug(boolean b) {
        isDebug = b;
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

    /**
     * 初始化密钥
     *
     * @param key1 私钥
     * @param key2 公钥
     */
    public static void initKey(String key1, String key2) {
        privateKey = key1;
        publicKey = key2;
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
        apiExceptionCallBacks = callBack;
    }

    /**
     * 获取状态处理回调
     *
     * @return APIExceptionCallBack回调接口
     */
    public static APICallBack getApiCallback() {
        return apiExceptionCallBacks;
    }

    public static EncodeDecodeKey getKey() {
        return Instance.key;
    }

//    public static boolean isOpenApiException() {
//        return mOpenApiException;
//    }
//
//    public static void setOpenApiException(boolean openApiException) {
//        mOpenApiException = openApiException;
//    }

    public static Application getContext() {
        return mContext;
    }


    public static Proxy getProxy() {
        return proxy;
    }


    public static HashSet<ParseInfo> getParseInfo() {
        return rxParseInfoSet;
    }

    public static void addParseInfo(ParseInfo parseInterceptor) {
        rxParseInfoSet.add(parseInterceptor);
    }

    public static OkHttpClient.Builder flatMapClient(OkHttpClient.Builder client) {
        return client;
    }

    /**
     * 加密对象单例
     */
    private static class Instance {
        private static EncodeDecodeKey key = getKey();

        private static EncodeDecodeKey getKey() {
            return new EncodeDecodeKey(privateKey, publicKey);
        }
    }
}
