package com.baozi.linfeng.location.rxandroid;

import android.text.TextUtils;
import android.widget.Toast;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.location.APIException;
import com.baozi.linfeng.location.onExceptionListener;

import io.reactivex.functions.Consumer;

public enum JErrorEnum implements Consumer<Throwable> {
    normal(0), toast(1);

    private int type;

    JErrorEnum(int type) {
        this.type = type;
    }

    public static void normal(Throwable throwable) {
        normal.accept(throwable);
    }

    public static void toast(Throwable throwable) {
        toast.accept(throwable);
    }

    @Override
    public void accept(Throwable throwable) {
        String errMsg = "";
        Class<? extends Throwable> throwableClass = throwable.getClass();
        //Api自定义异常消息,请求是成功的.如果有其它动作,在APICallBack中处理
        if (throwableClass.equals(APIException.class)) {
            errMsg = throwable.getMessage();
        }
        //处理error异常,http异常
        onExceptionListener exceptionListener = NetWorkManager.getExceptionListener();
        if (exceptionListener != null) {
            String exceptionMsg = exceptionListener.onError(throwable);
            if (!TextUtils.isEmpty(exceptionMsg)) {
                errMsg = exceptionMsg;
            }
        }
        if (type == 1 && !TextUtils.isEmpty(errMsg)) {
            Toast.makeText(NetWorkManager.getContext(), errMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
