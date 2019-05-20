package com.baozi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.location.SimpleParams;
import com.baozi.linfeng.location.retrofit.RetrofitUtil;
import com.baozi.linfeng.location.rxandroid.NetWorkTransformer;
import com.baozi.linfeng.location.rxandroid.RxParseInfo;
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
        NetWorkManager.init("https://www.apiopen.top/", this.getApplication());
        NetWorkManager.addParseInfo(
                new RxParseInfo("code", "data", "msg", new RxParseInfo.CheckSuccess() {
                    @Override
                    public boolean isSuccess(JsonObject asJsonObject) {
                        return "200".equals(asJsonObject.get("code").toString());
                    }
                }));
        Disposable journalismApi = RetrofitUtil.getApi(JApi.class)
                .BasePost("journalismApi",
                        SimpleParams.create()
                )
                .compose(new NetWorkTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String stringBaseResponse) throws Exception {
                        Log.i("data",stringBaseResponse);
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

