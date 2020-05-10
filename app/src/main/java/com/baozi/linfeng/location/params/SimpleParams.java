package com.baozi.linfeng.location.params;

import android.content.Context;

import com.baozi.linfeng.factory.JSONFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Jlanglang on 2017/9/4 0004.
 * 简书:http://www.jianshu.com/u/6bac141ea5fe
 */

public class SimpleParams extends HashMap<String, Object> {
    private LinkedHashMap<String, Rules> checkParams = new LinkedHashMap<>();

    public static SimpleParams create() {
        return new SimpleParams();
    }

    public SimpleParams putP(String key, Rules rules) {
        checkParams.put(key, rules);
        put(key, rules.getData());
        return this;
    }

    public SimpleParams putP(String key, Object value) {
        put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return JSONFactory.toJson(this);
    }

    public boolean check(Context context) {
        Set<String> strs = checkParams.keySet();
        for (Object str : strs) {
            Rules rules = checkParams.get(str);
            boolean check = rules.check(context);
            if (!check) {
                return false;
            }
        }
        return true;
    }
}


