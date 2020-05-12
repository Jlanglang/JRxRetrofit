package com.baozi.linfeng.location.rxandroid;

import com.baozi.linfeng.location.retrofit.parse.NetStringParseInfo;
import com.baozi.linfeng.location.retrofit.parse.IParse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.JSONFactory;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络请求通用设置转换器
 * 组件化的情况下,如果解析失败,请使用JsonArrayParesTransformer,JsonParesTransformer
 */
public class NetStringTransformer implements ObservableTransformer<String, String> {
    private static final NetStringTransformer netWork = new NetStringTransformer();

    public static ObservableTransformer<String, String> instance() {
        return netWork;
    }

    @Override
    public ObservableSource<String> apply(Observable<String> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .timeout(NetWorkManager.getDefaultTimeOut(), TimeUnit.SECONDS)
                .retry(NetWorkManager.getDefaultRetry())
                .map(response -> {
                    JsonElement jsonElement = JSONFactory.parseJson(response);
                    JsonObject asJsonObject = jsonElement.getAsJsonObject();

                    NetStringParseInfo parseInfo = getParseInfo(jsonElement);
                    if (parseInfo == null) {
                        return response;
                    }
                    return parseInfo.parse(asJsonObject);
                });
    }

    private NetStringParseInfo getParseInfo(JsonElement jsonElement) throws Exception {
        HashSet<IParse> parseInterceptors = NetWorkManager.getParseInfo();
        for (IParse p : parseInterceptors) { // 优先判断自定义添加的解析
            if (p instanceof NetStringParseInfo) {
                return (NetStringParseInfo) p;
            }
        }
        if (NetStringParseInfo.DEFAULT.isParse(jsonElement)) { // 判断默认的,如果不符合,说明配置错误.
            return NetStringParseInfo.DEFAULT;
        }
        return null;
    }
}