package com.baozi.linfeng.location.rxbus;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Cmad on 2016/4/26.
 */
public class SubscriberMethod {
    public Method method;
    public ThreadMode threadMode;
    public Class<?> eventType;
    public Object subscriber;
    public int code;

    public SubscriberMethod(Object subscriber, Method method, Class<?> eventType, int code,ThreadMode threadMode) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
        this.subscriber = subscriber;
        this.code = code;
    }


    /**
     * 调用方法
     * @param o 参数
     */
    public void invoke(Object o){
        try {
            method.invoke(subscriber, o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
