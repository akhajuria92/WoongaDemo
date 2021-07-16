package com.android.woonga.webapi;

import com.android.woonga.BuildConfig;
import com.android.woonga.R;
import com.android.woonga.WoongaApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public final String youtubeWebHost = "https://www.googleapis.com/youtube/v3/";


    private static RestClient RestClient = null;

    public static RestClient getInstance() {
        return (RestClient == null) ? RestClient = new RestClient() : RestClient;
    }

    public Retrofit retrofit = null;

    public WebApi getBaseUrl() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();

        return retrofit.create(WebApi.class);
    }

    public WebApi getYoutubeBaseUrl() {

        retrofit = new Retrofit.Builder()
                .baseUrl(youtubeWebHost)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();

        return retrofit.create(WebApi.class);
    }

    private OkHttpClient getClient() {
        long HTTP_TIMEOUT = 20;
        final OkHttpClient.Builder okHttpClientBuilder = new
                OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(interceptor);
        okHttpClientBuilder.connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        return okHttpClientBuilder.build();
    }
}
