package com.baozi.linfeng.location.rxandroid;

import com.google.gson.JsonObject;


public class RxParseInfo {
    public final static RxParseInfo DEFAULT =
            new RxParseInfo("code", "data", "msg", new CheckSuccess() {
                @Override
                public boolean isSuccess(JsonObject asJsonObject) {
                    return asJsonObject.get("code").toString().equals("0");
                }
            });

    private final String codeKey;
    private final String dataKey;
    private final String msgKey;
    private final CheckSuccess checkSuccess;

    public RxParseInfo(String codeKey, String dataKey, String msgKey, CheckSuccess checkSuccess) {
        this.codeKey = codeKey;
        this.dataKey = dataKey;
        this.msgKey = msgKey;
        this.checkSuccess = checkSuccess;
    }

    public boolean hasKey(JsonObject asJsonObject) throws Exception {
        boolean hasCode = false, hasData = false, hasMsg = false;
        if (codeKey != null) {
            hasCode = asJsonObject.has(codeKey);
        }
        if (dataKey != null) {
            hasData = asJsonObject.has(dataKey);
        }
        if (msgKey != null) {
            hasMsg = asJsonObject.has(msgKey);
        }
        return hasCode && hasData && hasMsg;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public String getDataKey() {
        return dataKey;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public CheckSuccess getCheckSuccess() {
        return checkSuccess;
    }

    public interface CheckSuccess {
        boolean isSuccess(JsonObject asJsonObject);
    }
}
