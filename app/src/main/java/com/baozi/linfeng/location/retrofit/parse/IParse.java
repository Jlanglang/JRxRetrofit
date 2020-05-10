package com.baozi.linfeng.location.retrofit.parse;


public interface IParse<T, R> {
    /**
     * 是否用该对象解析
     */
    boolean isParse(R data);

    /**
     * 解析
     */
    T parse(R data) throws Exception;
}
