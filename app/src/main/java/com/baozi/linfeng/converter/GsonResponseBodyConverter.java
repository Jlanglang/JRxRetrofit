package com.baozi.linfeng.converter;

import android.support.annotation.NonNull;

import com.baozi.linfeng.factory.JSONFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Type type;
    private Charset charset;

    public GsonResponseBodyConverter() {
    }

    public GsonResponseBodyConverter(Type type, Charset charset) {
        this.type = type;
        this.charset = charset;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        try {
            String json = value.string();
            return JSONFactory.fromJson(json, type);
        } finally {
            value.close();
        }
    }
}
