package com.baozi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baozi.linfeng.NetWorkManager;
import com.baozi.linfeng.location.SimpleParams;
import com.baozi.linfeng.location.retrofit.RetrofitUtil;
import com.baozi.linfeng.location.rxandroid.NetWorkTransformer;
import com.baozi.linfeng.location.rxandroid.RxParseInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.zip.Inflater;

import io.reactivex.Observable;
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
//        NetWorkManager.addInterceptor(new InflaterRequestInterceptor());
        NetWorkManager.init("https://www.apiopen.top/", "200", this.getApplication());
        NetWorkManager.addParseInfo(new RxParseInfo("code", "data", "msg"));
        RetrofitUtil.getApi(JApi.class)
                .BasePost("journalismApi",
                        SimpleParams.create()
                )
                .compose(new NetWorkTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String stringBaseResponse) throws Exception {
                        String data = stringBaseResponse;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
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

    public class InflaterRequestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (originalRequest.body() == null) {
                return chain.proceed(originalRequest);
            }
            Response proceed = chain.proceed(originalRequest);
            ResponseBody body = proceed.body();
            return proceed.newBuilder().body(inflater(body)).build();
        }

        private ResponseBody inflater(final ResponseBody body) {
            return new ResponseBody() {
                @Override
                public MediaType contentType() {
                    return body.contentType();
                }

                @Override
                public long contentLength() {
                    return -1; // 无法提前知道压缩后的数据大小
                }

                @Override
                public BufferedSource source() {
                    return Okio.buffer(new InflaterSource(body.source(), new Inflater(true)));
                }
            };
        }
    }
}

