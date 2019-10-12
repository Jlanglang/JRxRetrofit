package com.baozi.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.JSONFactory;
import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.SimpleParams;
import com.baozi.linfeng.location.retrofit.JApi;
import com.baozi.linfeng.location.retrofit.JApiImpl;
import com.baozi.linfeng.location.retrofit.RetrofitUtil;
import com.baozi.linfeng.location.rxandroid.DialogTransformer;
import com.baozi.linfeng.location.rxandroid.JErrorEnum;
import com.baozi.linfeng.location.rxandroid.JRxCompose;
import com.baozi.linfeng.location.rxandroid.SimpleObserver;
import com.baozi.linfeng.location.rxandroid.ToastObserver;
import com.baozi.myapplication.bean.Login;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String a="{\"code\":\"0\",\"data\":{\"regFrom\":\"mhealth\",\"uacId\":\"6002354038\",\"userId\":6002351370,\"ut\":\"SU14GWSa+9O9yBluJhC2phkLEbAj26KJdUvknt43eCVc4WEFGgTPJhDF2+wnjcqbZ2K+d+DA+vMWpzShc9ahDw==\"},\"msg\":\"成功\",\"success\":true}";
        JsonElement jsonElement = JSONFactory.parseJson(a);
        String code = JSONFactory.getValue(jsonElement, "code");
//        //不使用JApiImpl
//        Disposable login = RetrofitUtil.getApi(JApi.class)
//                .get("/login", SimpleParams.create()
//                        .putP("key1", 1)
//                        .putP("key2", 2)
//                        .putP("key3", 2)
//                        .putP("key4", 3)
//                )
//                .onErrorResumeNext(Observable.timer(5, TimeUnit.SECONDS).map(t -> "1"))
//                .compose(JRxCompose.normal())
//                .compose(new DialogTransformer<>(this))
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.i("log", s);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });
        // 使用SimpleObserver,解析返回Object类型的
//        JApiImpl.with(this)
//                .post("/Login", SimpleParams.create())
//                .compose(JRxCompose.obj(Login.class))
//                .subscribe(new SimpleObserver<Login>() {
//                    @Override
//                    public void call(Login login) {
//
//                    }
//                });
//        // 使用ToastObserver,解析返回集合类型的
//        JApiImpl.with(this)
//                .post("/Login", SimpleParams.create())
//                .compose(JRxCompose.array(Login.class))
//                .subscribe(new ToastObserver<List<Login>>() {
//                    @Override
//                    public void call(List<Login> logins) {
//
//                    }
//                });
//        Disposable subscribe = JApiImpl.with(this)
//                .get("", SimpleParams.create())
//                .compose(JRxCompose.obj(Login.class))
//                .subscribe(login1 -> {
//
//                }, JErrorEnum.toast);
    }
}

