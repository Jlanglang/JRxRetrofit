package com.baozi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.JSONFactory;
import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.SimpleParams;
import com.baozi.linfeng.location.retrofit.JApi;
import com.baozi.linfeng.location.retrofit.RetrofitUtil;
import com.baozi.linfeng.location.rxandroid.NetWorkTransformer;
import com.baozi.linfeng.location.rxandroid.RxParseInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetWorkManager.init("https://api.apiopen.top/", getApplication());
        NetWorkManager.addParseInfo(
                new RxParseInfo("code", "result", "message", "200")
                        .setCheckSuccess(new RxParseInfo.CheckSuccess() {
                            @Override
                            public boolean isSuccess(JsonObject jsonObject) {
                                return false;
                            }
                        })
        );
        NetWorkManager.setApiCallBack(new APICallBack() {
            @Override
            public String callback(String code, String resultData) {
                JsonElement jsonElement = JSONFactory.parseJson(resultData);
                return JSONFactory.getValue(jsonElement, "message");
            }
        });

        Disposable journalismApi = RetrofitUtil.getApi(JApi.class)
                .BasePost("recommendPoetry",
                        SimpleParams.create()
                )
                .compose(new NetWorkTransformer())
                .subscribe(stringBaseResponse -> {
                    Log.i("123", stringBaseResponse);
                }, e -> {
                    e.printStackTrace();
                });
        setContentView(R.layout.activity_main);
    }
}

