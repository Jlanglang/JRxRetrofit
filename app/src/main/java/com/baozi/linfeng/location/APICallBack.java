package com.baozi.linfeng.location;

/**
 * Created by baozi on 2017/10/18.
 */

public interface APICallBack {
    /**
     * @param code       请求的code
     * @param msg        服务器返回的消息
     * @param resultData 网络请求数据
     * @return error 消息
     */
    String callback(String code, String msg, String resultData);
}
