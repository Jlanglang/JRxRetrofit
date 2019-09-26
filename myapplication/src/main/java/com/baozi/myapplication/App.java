package com.baozi.myapplication;

import android.app.Application;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.JSONFactory;
import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.retrofit.ParseInfo;
import com.google.gson.JsonElement;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.init("https://api.apiopen.top/", this);
        NetWorkManager.addParseInfo(
                new ParseInfo("code", "result", "message", "200")
        );
        NetWorkManager.setApiCallBack(new APICallBack() {
            @Override
            public String callback(String code, String resultData) {
                JsonElement jsonElement = JSONFactory.parseJson(resultData);
                return JSONFactory.getValue(jsonElement, "message");
            }
        });
    }
}
