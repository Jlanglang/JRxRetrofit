package com.baozi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.factory.JSONFactory;
import com.baozi.linfeng.location.APICallBack;
import com.baozi.linfeng.location.SimpleParams;
import com.baozi.linfeng.location.retrofit.JApi;
import com.baozi.linfeng.location.retrofit.RetrofitUtil;
import com.baozi.linfeng.location.rxandroid.NetWorkTransformer;
import com.baozi.linfeng.location.rxandroid.RxParseInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.zip.Inflater;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.InflaterSource;
import okio.Okio;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetWorkManager.init("https://api.apiopen.top/", getApplication());
        NetWorkManager.setApiCallBack(new APICallBack() {
            @Override
            public String callback(String code, String resultData) {
                if (code.equals("1")) {
                    return "状态不对";
                }
                if (code.equals("200")) {
                    return "脑子不对";
                }
                JsonElement jsonElement = JSONFactory.parseJson(resultData);
                return JSONFactory.getValue(jsonElement, "msg");
            }
        });

        NetWorkManager.addParseInfo(
                new RxParseInfo("code", "result", "message", "100")
        );
        Disposable journalismApi = RetrofitUtil.getApi(JApi.class)
                .BasePost("recommendPoetry",
                        SimpleParams.create()
                )
                .compose(new NetWorkTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String stringBaseResponse) throws Exception {
                        Log.i("data", stringBaseResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        setContentView(R.layout.activity_main);
    }

    /**
     * Created by Jlanglang on 2017/8/29 0029.
     * 简书:http://www.jianshu.com/u/6bac141ea5fe
     */

    public interface JApi {

        /**
         * 上传文件
         *
         * @param url
         * @param params
         * @return
         */
        @POST
        @Multipart
        Observable<String> BasePost(@Url String url, @PartMap HashMap<String, RequestBody> params);

        /**
         * 通用POST
         *
         * @param url
         * @param json
         * @return
         */
        @POST
        Observable<String> BasePost(@Url String url, @Body String json);

        /**
         * 通用POST
         *
         * @param url
         * @param json
         * @return
         */
        @POST
        Observable<String> BasePost(@Url String url, @Body SimpleParams json);

        /**
         * 通用get
         *
         * @param url
         * @return
         */
        @GET
        Observable<String> BaseGet(@Url String url, @QueryMap SimpleParams params);

    }
}

