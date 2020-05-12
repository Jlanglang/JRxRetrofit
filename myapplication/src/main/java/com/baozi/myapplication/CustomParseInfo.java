package com.baozi.myapplication;

import com.baozi.linfeng.location.APIException;
import com.baozi.linfeng.location.retrofit.parse.NetStringParseInfo;
import com.google.gson.JsonElement;

public class CustomParseInfo extends NetStringParseInfo {
    @Override
    public boolean isParse(JsonElement jsonElement) {
        return true;
    }

    @Override
    public String parse(JsonElement jsonElement) throws APIException {
        return super.parse(jsonElement);
    }
}
