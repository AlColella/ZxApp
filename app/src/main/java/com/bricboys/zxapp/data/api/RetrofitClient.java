package com.bricboys.zxapp.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit zxinfoRetrofit = null;
    private static Retrofit zxartRetrofit = null;

    public static ZxinfoApi getZxinfoApi() {
        if (zxinfoRetrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            zxinfoRetrofit = new Retrofit.Builder()
                    .baseUrl("https://api.zxinfo.dk/v5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return zxinfoRetrofit.create(ZxinfoApi.class);
    }

    public static ZxartApi getZxartApi() {
        if (zxartRetrofit == null) {
            // ZXArt needs a different approach due to its non-standard URL structure
            zxartRetrofit = new Retrofit.Builder()
                    .baseUrl("https://zxart.ee/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return zxartRetrofit.create(ZxartApi.class);
    }
}
