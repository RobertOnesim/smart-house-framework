package com.ronesim.smarthouse.remote;

/**
 * Created by ronesim on 13.04.2017.
 */

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofitBase = null;
    private static Retrofit retrofitTokenAccess = null;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit getClient(String baseUrl) {
        if (retrofitBase == null) {
            retrofitBase = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitBase;
    }

    public static Retrofit getClient(String baseUrl, String token) {
        if (retrofitTokenAccess == null) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(token);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                retrofitTokenAccess = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
            }
        }
        return retrofitTokenAccess;
    }
}
