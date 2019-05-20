# JRxRetrofit

这是一个rxjava2+retrofit2的封装网络框架.目的是快速开发,快速配置.


# 初始化:

首先.你需要

```
init(String baseUrl, Application context)
```

--- 


然后,你需要定义自己的basebean规则,

# RxParseInfo
```
public RxParseInfo(String codeKey, String dataKey, String msgKey, String successCode) {
        this.codeKey = codeKey;
        this.dataKey = dataKey;
        this.msgKey = msgKey;
        this.successCode = successCode;
    }
```

使用:
```

 NetWorkManager.addParseInfo(
                new RxParseInfo("code", "data", "msg", "200") //200的意思是成功的code.
        );

```

这段代码代表着.你的basebean的格式是.等价于
```
class BaseBean<T>{
  String code;
  T data;
  String msg;

}

```

但是此框架,不需要BaseBean.只需要添加RxParseInfo.对应你的接口规则即可



# 如何判断接口请求成功的

RxParseInfo 里默认通过判断上面的`successCode`与返回的`codeKey`的值进行比较的

```
 public boolean isSuccess(JsonObject asJsonObject) {
        if (checkSuccess != null) {
            return checkSuccess.isSuccess(asJsonObject);
        }
        String code = asJsonObject.get(codeKey).toString();
        return TextUtils.equals(code, successCode);
    }
```

# 如何自定义请求成功判断
```
new RxParseInfo("code", "data", "msg", "200")
 .setCheckSuccess(new RxParseInfo.CheckSuccess() {
                    @Override
                    public boolean isSuccess(JsonObject asJsonObject) {
                        return false;
                    }
                })

```


