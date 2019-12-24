package com.baozi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.baozi.linfeng.factory.JSONFactory;
import com.baozi.linfeng.location.params.Rules;
import com.baozi.linfeng.location.params.SimpleParams;
import com.baozi.linfeng.location.retrofit.JApiImpl;
import com.baozi.linfeng.location.rxandroid.JRxCompose;
import com.baozi.linfeng.location.rxandroid.NetWorkTransformer;
import com.baozi.linfeng.location.rxandroid.SimpleObserver;
import com.baozi.myapplication.bean.Login;
import com.google.gson.JsonElement;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String a = "{\"code\":\"0\",\"data\":{\"regFrom\":\"mhealth\",\"uacId\":\"6002354038\",\"userId\":6002351370,\"ut\":\"SU14GWSa+9O9yBluJhC2phkLEbAj26KJdUvknt43eCVc4WEFGgTPJhDF2+wnjcqbZ2K+d+DA+vMWpzShc9ahDw==\"},\"msg\":\"成功\",\"success\":true}";
//        JsonElement jsonElement = JSONFactory.parseJson(a);
//        String code = JSONFactory.getValue(jsonElement, "code");
//        String data = JSONFactory.getValue(jsonElement, "data");
//        String msg = JSONFactory.getValue(jsonElement, "msg");
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
//         使用SimpleObserver,解析返回Object类型的
//        SimpleParams params = SimpleParams.create()
//                .putP("key", Rules.require(9).max(10).msg("key必须小于10"))
//                .putP("a", 123)
//                .putP("b", 234)
//                .putP("c", 123)
//                .putP("data1", Rules.normal(2).min(1).msg("data1必须大于1"))
//                .putP("data2", Rules.normal(4).max(10).msg("data2必须小于10"))
//                .putP("data3", Rules.normal(5));
//        if (!params.check(this)) {
//            return;
//        }
//        JApiImpl.with(this)
//                .post("/Login", params)
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

