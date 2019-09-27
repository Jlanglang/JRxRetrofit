package com.baozi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.JSONFactory;
import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.SimpleParams;
import com.baozi.linfeng.location.retrofit.JApi;
import com.baozi.linfeng.location.retrofit.JApiImpl;
import com.baozi.linfeng.location.retrofit.ParseInfo;
import com.baozi.linfeng.location.retrofit.RetrofitUtil;
import com.baozi.linfeng.location.rxandroid.JRxCompose;
import com.baozi.linfeng.location.rxandroid.SimpleObserver;
import com.baozi.linfeng.location.rxandroid.ToastObserver;
import com.baozi.myapplication.bean.Login;
import com.google.gson.JsonElement;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //不使用JApiImpl
        Disposable login = RetrofitUtil.getApi(JApi.class)
                .get("/login", SimpleParams.create()
                        .putP("key1", 1)
                        .putP("key2", 2)
                        .putP("key3", 2)
                        .putP("key4", 3)
                )
                .compose(JRxCompose.normal())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });
        // 使用SimpleObserver,解析返回Object类型的
        JApiImpl.with(this)
                .post("/Login", SimpleParams.create())
                .compose(JRxCompose.obj(Login.class))
                .subscribe(new SimpleObserver<Login>() {
                    @Override
                    public void call(Login login) {

                    }
                });
        // 使用ToastObserver,解析返回集合类型的
        JApiImpl.with(this)
                .post("/Login", SimpleParams.create())
                .compose(JRxCompose.array(Login.class))
                .subscribe(new ToastObserver<List<Login>>() {
                    @Override
                    public void call(List<Login> logins) {

                    }
                });
    }
}

