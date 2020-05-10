package com.baozi.linfeng.location.retrofit.parse;

import android.text.TextUtils;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.JSONFactory;
import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.APIException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class NetStringParseInfo implements IParse<String, JsonElement> {
    public static final NetStringParseInfo DEFAULT =
            new NetStringParseInfo("code", "data", "msg", "200");

    private final String codeKey;
    private final String dataKey;
    private final String msgKey;
    private final String successCode;
    private CheckSuccess checkSuccess;

    public NetStringParseInfo(String codeKey, String dataKey, String msgKey, String successCode) {
        this.codeKey = codeKey;
        this.dataKey = dataKey;
        this.msgKey = msgKey;
        this.successCode = successCode;
    }


    public NetStringParseInfo setCheckSuccess(CheckSuccess checkSuccess) {
        this.checkSuccess = checkSuccess;
        return this;
    }

    @Override
    public boolean isParse(JsonElement jsonElement) {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        if (!asJsonObject.has(codeKey)) {
            return false;
        }
        if (asJsonObject.has(dataKey)) {
            return false;
        }
        if (asJsonObject.has(msgKey)) {
            return false;
        }
        return true;
    }

    public String parse(JsonElement jsonElement) throws APIException {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        if (checkSuccess != null) {
            boolean success = checkSuccess.isSuccess(asJsonObject);
            if (success) {
                return JSONFactory.getValue(jsonElement, dataKey);
            }
        }
        String msg = JSONFactory.getValue(jsonElement, msgKey);
        String code = JSONFactory.getValue(jsonElement, codeKey);
        String errorMsg = null;
        //通过code获取注册的接口回调.
        APICallBack apiCallback = NetWorkManager.getApiCallback();
        if (apiCallback != null) {
            String callbackMsg = apiCallback.callback(code, jsonElement.toString());
            if (!TextUtils.isEmpty(callbackMsg)) {
                errorMsg = callbackMsg;
            }
        }
        //如果callback不处理,并打开isOpenApiException,则抛出服务器返回msg信息
        if (TextUtils.isEmpty(errorMsg) && NetWorkManager.isOpenApiException()) {
            errorMsg = msg;
        }
        //抛出异常,走到onError.
        throw new APIException(code, errorMsg);
    }

    public interface CheckSuccess {
        boolean isSuccess(JsonObject jsonObject);
    }
}
