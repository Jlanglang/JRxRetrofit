package com.baozi.linfeng.location.retrofit.parse;


public interface IParse<T, R> {
    boolean isParse(R data);

    T parse(R data) throws Exception;
}
