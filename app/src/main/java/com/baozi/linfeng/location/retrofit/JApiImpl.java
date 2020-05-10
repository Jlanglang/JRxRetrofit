package com.baozi.linfeng.location.retrofit;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.baozi.linfeng.location.params.SimpleParams;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class JApiImpl implements JApi, LifecycleObserver, ObservableTransformer<String, String> {

    public static JApi getApi() {
        return RetrofitUtil.getApi(JApi.class);
    }

    public static JApiImpl with(Lifecycle lifecycle) {
        JApiImpl JApiImpl = new JApiImpl();
        lifecycle.addObserver(JApiImpl);
        return JApiImpl;
    }

    private Disposable disposable;

    @Override
    public ObservableSource<String> apply(Observable<String> upstream) {
        return upstream.doOnSubscribe(disposable -> this.disposable = disposable);
    }

    private void cancel() {
        if (this.disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        this.cancel();
    }

    @Override
    public Observable<String> post(String url, HashMap<String, RequestBody> params) {
        return getApi().post(url, params).compose(this);
    }

    @Override
    public Observable<String> post(String url, String json) {
        return getApi().post(url, json).compose(this);
    }

    @Override
    public Observable<String> post(String url, SimpleParams params) {
        return getApi().post(url, params).compose(this);
    }

    @Override
    public Observable<String> get(String url, SimpleParams params) {
        return getApi().get(url, params).compose(this);
    }


}
