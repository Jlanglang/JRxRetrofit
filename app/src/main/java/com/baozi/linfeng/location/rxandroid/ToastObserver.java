package com.baozi.linfeng.location.rxandroid;


import io.reactivex.disposables.CompositeDisposable;

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
        JErrorEnum.toast(e);
    }
}
