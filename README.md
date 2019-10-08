# JRxRetrofit

这是一个rxjava2+retrofit2的封装网络框架.目的是快速开发,快速配置.


# 依赖:

```
  implementation 'com.github.Jlanglang:JRxRetrofit:1.0.4'
```
根build.gradle里面添加
```
 repositories {
     maven { url 'https://jitpack.io' }
 }
```

# 使用:

首先.你需要

```
NetWorkManager.init(String baseUrl, Application context)
```


然后,你需要定义自己的basebean规则

```

 NetWorkManager.addParseInfo(
                new ParseInfo("code", "data", "msg", "200") //200的意思是成功的code.
        );

```

ParseInfo
```
public ParseInfo(String codeKey, String dataKey, String msgKey, String successCode) {
        this.codeKey = codeKey;
        this.dataKey = dataKey;
        this.msgKey = msgKey;
        this.successCode = successCode;
    }
    ...
```

ParseInfo等价于你的basebean的格式是.也就是接口返回规则
```
class BaseBean<T>{
  String code;
  T data;
  String msg;

}

```

但是此框架,不需要BaseBean.只需要添加ParseInfo.对应你的接口规则即可



# 如何判断接口请求成功的

ParseInfo 里默认通过判断上面的`successCode`与返回的`codeKey`的值进行比较的

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
使用setCheckSuccess().非必须.主要是为了扩展.
```
new ParseInfo("code", "data", "msg", "200")
 .setCheckSuccess(new RxParseInfo.CheckSuccess() {
                    @Override
                    public boolean isSuccess(JsonObject asJsonObject) {
                        return false;
                    }
                })
```
# 初始化
```
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.init("https://api.apiopen.top/", this);
        NetWorkManager.initKey("私钥", "公钥");//加密解密
        NetWorkManager.setDefaultRetry(5);//重试次数
        NetWorkManager.setDefaultTimeOut(20);//秒
        NetWorkManager.addParseInfo(
                new ParseInfo("code", "result", "message", "200")
        );
        NetWorkManager.setExceptionListener(new onExceptionListener() {
            @Override
            public String onError(Throwable throwable) {
                if (throwable instanceof NullPointerException) {

                }
                if (throwable instanceof NetworkErrorException) {

                }
                if (throwable instanceof SocketException) {
                    
                }
                return null;
            }
        });
        NetWorkManager.setApiCallBack(new APICallBack() {
            @Override
            public String callback(String code, String resultData) {
                JsonElement jsonElement = JSONFactory.parseJson(resultData);
                return JSONFactory.getValue(jsonElement, "message");
            }
        });
    }
}
```
# 异常的处理逻辑

```
        String errorMsg = null;
                    //通过code获取注册的接口回调.
                    APICallBack apiCallback = NetWorkManager.getApiCallback();
                    if (apiCallback != null) {
                        String callbackMsg = apiCallback.callback(code, response);
                        if (!TextUtils.isEmpty(callbackMsg)) {
                            errorMsg = callbackMsg;
                        }
                    }
                    //如果callback不处理,则抛出服务器返回msg信息
                    if (TextUtils.isEmpty(errorMsg)) {
                        errorMsg = msg;
                    }
                    //抛出异常,走到onError.
                    throw new APIException(code, errorMsg);
```

# 异常消息处理
这里写了一个枚举.用来处理异常消息.
```
public enum JErrorEnum implements Consumer<Throwable> {
    normal(0), toast(1);

    private int type;

    JErrorEnum(int type) {
        this.type = type;
    }

    public static void normal(Throwable throwable) {
        normal.accept(throwable);
    }

    public static void toast(Throwable throwable) {
        toast.accept(throwable);
    }

    @Override
    public void accept(Throwable throwable) {
        String errMsg = "";
        Class<? extends Throwable> throwableClass = throwable.getClass();
        //处理Api自定义异常处理,请求是成功的,如果需要特殊处理,使用APICallBack
        if (throwableClass.equals(APIException.class)) {
            errMsg = throwable.getMessage();
        }
        //处理error异常,http异常
        onExceptionListener exceptionListener = NetWorkManager.getExceptionListener();
        if (exceptionListener != null) {
            errMsg = exceptionListener.onError(throwable);
        }
        if (type == 1 && !TextUtils.isEmpty(errMsg)) {
            Toast.makeText(NetWorkManager.getContext(), errMsg, Toast.LENGTH_SHORT).show();
        }
    }
}
```
### 你可以用以下几种方式使用:

#### SimpleObserver

`normal`默认只处理异常逻辑,不会弹消息
```
 @Override
  public void onError(Throwable e) {
     JErrorEnum.normal(e);
 }
```


#### ToastObserver

`toast`会弹消息
```
 @Override
  public void onError(Throwable e) {
     JErrorEnum.toast(e);
 }
```

#### 直接用
```
    Disposable subscribe = JApiImpl.with(this)
                .get("", SimpleParams.create())
                .compose(JRxCompose.obj(Login.class))
                .subscribe(login1 -> {

                }, JErrorEnum.toast);
```
如果不使用`JErrorEnum`的话.下面的设置就会失效,注意一个请求内不要重复使用哦.
# 设置全局异常统一回调
```
   NetWorkManager.setExceptionListener(new onExceptionListener() {
            @Override
            public String onError(Throwable throwable) {
                return null;
            }
        });
```


# 简单例子:

```
      //不使用JApiImpl
          Disposable login = RetrofitUtil.getApi(JApi.class)
                  .get("/login", SimpleParams.create()
                          .putP("key1", 1)
                          .putP("key2", 2)
                          .putP("key3", 2)
                          .putP("key4", 3)
                  )
                  .compose(JRxCompose.normal())
                  .subscribe(new Consumer<String>() {
                      @Override
                      public void accept(String s) throws Exception {
  
                      }
                  }, JErrorEnum.toast);
          // 使用SimpleObserver,解析返回Object类型的
          JApiImpl.with(this)
                  .post("/Login", SimpleParams.create())
                  .compose(JRxCompose.obj(Login.class))
                  .subscribe(new SimpleObserver<Login>() {
                      @Override
                      public void call(Login login) {
  
                      }
                  });
          // 使用ToastObserver,解析返回集合类型的
          JApiImpl.with(this)
                  .post("/Login", SimpleParams.create())
                  .compose(JRxCompose.array(Login.class))
                  .subscribe(new ToastObserver<List<Login>>() {
                      @Override
                      public void call(List<Login> logins) {
  
                      }
                  });
```

