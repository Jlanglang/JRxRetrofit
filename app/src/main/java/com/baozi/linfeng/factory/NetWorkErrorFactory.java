package com.baozi.linfeng.factory;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.location.APIException;
import com.baozi.linfeng.location.onExceptionListener;

/**
 * Created by baozi on 2017/10/18.
 */
@Deprecated
public class NetWorkErrorFactory {
    /**
     * 异常时处理工厂
     *
     * @param throwable 异常
     * @return 获取异常提示消息
     */
    @Deprecated
    public static String disposeError(Throwable throwable) {
        Class<? extends Throwable> throwableClass = throwable.getClass();
        //处理Api自定义异常处理,请求是成功的,如果需要特殊处理,使用APICallBack
        if (throwableClass.equals(APIException.class)) {
            return throwable.getMessage();
        }
        //处理error异常,http异常
        onExceptionListener exceptionListener = NetWorkManager.getExceptionListener();
        if (exceptionListener != null) {
            return exceptionListener.onError(throwable);
        }
        return "";
    }
}
