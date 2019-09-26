package com.baozi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.JSONFactory;
import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.SimpleParams;
import com.baozi.linfeng.location.retrofit.JApiImpl;
import com.baozi.linfeng.location.retrofit.ParseInfo;
import com.baozi.linfeng.location.rxandroid.JRxCompose;
import com.google.gson.JsonElement;

import io.reactivex.disposables.Disposable;

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
                .compose(JRxCompose.normal())
                .subscribe(s -> {

                });

        setContentView(R.layout.activity_main);
    }
}

