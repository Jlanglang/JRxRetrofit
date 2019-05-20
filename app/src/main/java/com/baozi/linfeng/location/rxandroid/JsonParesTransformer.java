package com.baozi.linfeng.location.rxandroid;

import android.text.TextUtils;

import com.baozi.linfeng.factory.JSONFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jlanglang on 2017/8/31 0031.
 * 简书:http://www.jianshu.com/u/6bac141ea5fe
 */

public class JsonParesTransformer<T> implements ObservableTransformer<String, T> {
    private final Class<T> zClass;

    public JsonParesTransformer(Class<T> zClass) {
        this.zClass = zClass;
    }

    @Override
    public ObservableSource<T> apply(Observable<String> upstream) {
        return upstream.compose(new NetWorkTransformer())
                .observeOn(Schedulers.computation())
                .flatMap(s -> Observable
                        .just(JSONFactory.fromJson(TextUtils.isEmpty(s) ? "{}" : s, zClass)))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
