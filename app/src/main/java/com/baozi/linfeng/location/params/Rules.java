package com.baozi.linfeng.location.params;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

//校验类
public class Rules {


    public enum Type {
        Str, Num, Obj
    }

    private CustomCheck customCheck;
    private Type type = Type.Str;
    private String msg = "参数校验失败";
    private String[] values;
    private double min, max;
    private String reg;

    private Object data;
    private boolean isRequire = false;

    public Rules(Object data) {
    }

    public static Rules normal(Object data) {
        return new Rules(data);
    }

    public static Rules require(Object data) {
        return normal(data).isRequire();
    }

    private Rules isRequire() {
        isRequire = true;
        return this;
    }

    public Rules custom(CustomCheck customCheck) {
        this.customCheck = customCheck;
        return this;
    }

    public Rules values(String... values) {
        this.values = values;
        return this;
    }

    public Rules regex(String reg) {
        this.reg = reg;
        return this;
    }

    public Rules msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Rules range(double min, double max) {
        this.min = min;
        this.max = max;
        return this;
    }

    Object getData() {
        return data;
    }

    boolean check(Context context) {
        if (customCheck != null) {
            return customCheck.call(data);
        }
        boolean b = valueCheck();
        if (!b) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        return b;
    }

     boolean valueCheck() {
        if (type == Type.Str) {
            String str = (String) data;
            if (isRequire && TextUtils.isEmpty(str)) {
                return false;
            }
            //如果没有值,或者没有正则,无需校验,直接返回
            if (values != null) {
                for (String s : values) {
                    if (s.equals(str)) {
                        return false;
                    }
                }
            }
            return str.matches(reg);
        }
        if (type == Type.Num) {
            double numData = (double) data;
            return numData > min && numData < max;
        }
        if (type == Type.Obj) {
            return isRequire && data != null;
        }
        return true;
    }

    public interface CustomCheck {
        boolean call(Object obj);
    }
}