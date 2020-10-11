package com.mainsm.apmc.retrofit;

import com.mainsm.apmc.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static RetrofitClient mInstance = null;
    private static final String BASE_URL = "https://api.backendless.com/" + BuildConfig.APP_KEY_BACKENDLESS +
            "/" + BuildConfig.REST_API_KEY_BACKENDLESS + "/";
    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

}
