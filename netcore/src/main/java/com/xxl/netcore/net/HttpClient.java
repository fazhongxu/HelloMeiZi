package com.xxl.netcore.net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xxl on 2018/10/9.
 * Description :
 */

public class HttpClient {

    private OkHttpClient mOkHttpClient;

    public HttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        }
    }

    public void get(String url, final Callback callback) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    callback.onResponse(response.body().string());
                }
            }
        });
    }
}
