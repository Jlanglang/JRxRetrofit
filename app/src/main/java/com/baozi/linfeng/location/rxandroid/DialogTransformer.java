package com.baozi.linfeng.location.rxandroid;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.JSONFactory;
import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.APIException;
import com.baozi.linfeng.location.retrofit.ParseInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.linfeng.rx_retrofit_network.R;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * dialog
 */
public class DialogTransformer<T> implements ObservableTransformer<T, T> {
    public Dialog dialog;

    public DialogTransformer(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream
                .doFinally(() -> dialog.dismiss())
                .doOnSubscribe(disposable -> dialog.show());
    }
}