package com.baozi.myapplication;

import android.accounts.NetworkErrorException;
import android.app.Application;
import android.support.annotation.NonNull;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.JSONFactory;
import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.onExceptionListener;
import com.baozi.linfeng.location.params.SimpleParams;
import com.baozi.linfeng.location.retrofit.GetCacheInterceptor;
import com.baozi.linfeng.location.retrofit.JApiImpl;
import com.baozi.linfeng.location.retrofit.ParseInfo;
import com.baozi.linfeng.location.rxandroid.JErrorEnum;
import com.baozi.linfeng.location.rxandroid.JRxCompose;
import com.baozi.linfeng.location.rxandroid.NetWorkTransformer;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Response;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.addInterceptor(new GetCacheInterceptor());
        NetWorkManager.initKey("私钥", "公钥");//加密解密
        NetWorkManager.setDefaultRetry(5);//重试次数
        NetWorkManager.setDefaultTimeOut(20);//秒
//        NetWorkManager.addParseInfo(
//                new ParseInfo("code", "result", "message", "200")
//        );
        NetWorkManager.setExceptionListener(throwable -> {
            if (throwable instanceof NullPointerException) {

            }
            if (throwable instanceof NetworkErrorException) {

            }
            if (throwable instanceof SocketException) {

            }
            return null;
        });
        NetWorkManager.setApiCallBack((code, msg, resultData) -> {
            if (code.equals("100")) {
                //跳转登陆页面
                return "登陆过期";
            }
            return msg;
        });
        NetWorkManager.init("https://api.apiopen.top/", this);
        JApiImpl.getApi().get("https://open.uczzd.cn/openiflow/openapi/v3/channels?app=haibao-iflow&access_token=1577165899199-bd5d18aad8dd8c7773a5395fe06b6c0c-05b8595b72f601448a0ab69095c252df&nt=99&imei=869454038266234&dn=6a03b3c0ce452a27&fr=android&oaid=&ve=1.0"
                ,
                SimpleParams.create()
        ).compose(new NetWorkTransformer()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.toString();
            }
        });
    }
}
