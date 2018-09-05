package com.dev.ds.coloradomountains;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.Observable;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class PhotoService {

    private static PhotoService instance;
    private static String BASE_URL = "https://api.flickr.com/services/";
    private Retrofit retrofitInstance;
    private PhotoApi photoApi;

    public PhotoService() {
        // Add logging for Retrofit calls
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Setup retrofit client using GSON and RxJava
        retrofitInstance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        photoApi = retrofitInstance.create(PhotoApi.class);
    }

    public PhotoApi getPhotoApi() {
        return photoApi;
    }

    public static PhotoService getInstance() {
        if (instance == null) {
            instance = new PhotoService();
        }
        return instance;
    }

}
