package com.baozi.linfeng.location.rxandroid;


import android.text.TextUtils;
import android.widget.Toast;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.NetWorkErrorFactory;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author jlanglang  2016/11/14 17:32
 */
public abstract class ToastObserver<T> extends SimpleObserver<T> {

    public ToastObserver() {
        this(null);
    }

    public ToastObserver(CompositeDisposable com) {
        super(com);
    }

    @Override
    public void onError(Throwable e) {
        String errorMsg = NetWorkErrorFactory.disposeError(e);
        if (!TextUtils.isEmpty(errorMsg)) {
            Toast.makeText(NetWorkManager.getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
