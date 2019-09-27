package com.baozi.linfeng.location.rxandroid;

import io.reactivex.ObservableTransformer;

public class JRxCompose {

    // 正常的网络请求ObservableTransformer
    public static ObservableTransformer<String, String> normal() {
        return NetWorkTransformer.instance();
    }

    // 简单的ObservableTransformer,没有网络请求的配置
    public static <T> SimpleTransformer<T> simple() {
        return new SimpleTransformer<T>();
    }

    // NetWorkTransformer的子类,用于结果是Object类型的
    public static <T> JsonParesTransformer<T> obj(Class<T> zClass) {
        return new JsonParesTransformer<T>(zClass);
    }

    // NetWorkTransformer的子类,用于结果是Array类型的
    public static <T> JsonArrayParesTransformer<T> array(Class<T> zClass) {
        return new JsonArrayParesTransformer<T>(zClass);
    }
}
