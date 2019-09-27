package com.baozi.linfeng.location.rxandroid;



import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author jlanglang  2016/11/14 17:32
 * @版本 2.0
 * @Change
 */
public abstract class SimpleObserver<T> implements Observer<T> {
    protected Disposable mDisposable;
    private CompositeDisposable mCompositeDisposable;

    public SimpleObserver() {
        this(null);
    }


    public SimpleObserver(CompositeDisposable com) {
        mCompositeDisposable = com;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        mDisposable = d;
        try {
            if (mCompositeDisposable == null) {
                return;
            }
            mCompositeDisposable.add(d);
        } catch (Exception ignored) {

        }
    }

    @Override
    public void onNext(T t) {
        call(t);
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {

    }

    public abstract void call(@NonNull T t);
}
