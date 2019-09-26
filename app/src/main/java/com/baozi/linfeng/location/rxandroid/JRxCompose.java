package com.baozi.linfeng.location.rxandroid;

import io.reactivex.ObservableTransformer;

public class JRxCompose {

    public static ObservableTransformer<String, String> netWork() {
        return NetWorkTransformer.instance();
    }

    public static <T> SimpleTransformer<T> simple() {
        return new SimpleTransformer<T>();
    }

    public static <T> JsonParesTransformer<T> array(Class<T> zClass) {
        return new JsonParesTransformer<T>(zClass);
    }

    public static <T> JsonArrayParesTransformer<T> obj(Class<T> zClass) {
        return new JsonArrayParesTransformer<T>(zClass);
    }
}
