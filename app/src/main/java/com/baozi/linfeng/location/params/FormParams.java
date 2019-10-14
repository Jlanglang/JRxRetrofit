package com.baozi.linfeng.location.params;

import android.content.Context;
import java.util.LinkedHashMap;
import java.util.Set;

public class FormParams extends SimpleParams {
    private LinkedHashMap<String, Rules> checkParams = new LinkedHashMap<>();

    public static FormParams create() {
        return new FormParams();
    }

    public FormParams putP(String key, Rules rules) {
        checkParams.put(key, rules);
        put(key, rules.getData());
        return this;
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
