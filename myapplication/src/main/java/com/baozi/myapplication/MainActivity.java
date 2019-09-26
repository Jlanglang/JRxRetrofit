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
        setContentView(R.layout.activity_main);
        Disposable recommendPoetry = JApiImpl.with(this)
                .post("recommendPoetry", SimpleParams.create())
                .compose(JRxCompose.normal())
                .subscribe(s -> {

                });

    }
}

