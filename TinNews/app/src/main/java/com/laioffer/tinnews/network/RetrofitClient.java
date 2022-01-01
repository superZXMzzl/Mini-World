package com.laioffer.tinnews.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String API_KEY = "a69ae6ec133240cda6f479283bb95c39";
    private static final String BASE_URL = "https://newsapi.org/v2/";

    //use "Builder Pattern" to build a retrofit
    public static Retrofit newInstance() {
        //build a HTTP Client
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //header interceptor to handle API token
                .addInterceptor(new HeaderInterceptor())
                //network interceptor to debug by view network requests and local database content
                .addNetworkInterceptor(new StethoInterceptor())

                .build();

        //build a Retrofit with HTTP Client
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    //header class to handle API token
    private static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original
                    .newBuilder()
                    .header("X-Api-Key", API_KEY)
                    .build();
            return chain.proceed(request);
        }
    }
}
