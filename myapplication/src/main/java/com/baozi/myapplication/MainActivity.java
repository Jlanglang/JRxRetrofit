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
import com.baozi.linfeng.location.rxandroid.NetWorkTransformer;
import com.google.gson.JsonElement;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetWorkManager.init("https://api.apiopen.top/", getApplication());
        NetWorkManager.addParseInfo(
                new ParseInfo("code", "result", "message", "200")
//                        .setCheckSuccess(new ParseInfo.CheckSuccess() {
//                            @Override
//                            public boolean isSuccess(JsonObject jsonObject) {
//                                return false;
//                            }
//                        })
        );
        //
        NetWorkManager.setApiCallBack(new APICallBack() {
            @Override
            public String callback(String code, String resultData) {
                JsonElement jsonElement = JSONFactory.parseJson(resultData);
                return JSONFactory.getValue(jsonElement, "message");
            }
        });
        Disposable recommendPoetry = JApiImpl.with(this)
                .post("recommendPoetry", SimpleParams.create())
                .compose(JRxCompose.netWork())
                .subscribe(s -> {

                });
        RetrofitUtil.getApi(JApi.class)
                .post("recommendPoetry", SimpleParams.create())
                .compose(new NetWorkTransformer())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        setContentView(R.layout.activity_main);
    }
}

