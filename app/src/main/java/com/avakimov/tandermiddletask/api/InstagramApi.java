package com.avakimov.tandermiddletask.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Andrew on 31.01.2018.
 */

public class InstagramApi {
    public static InstagramService createInstagramService() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.instagram.com/v1");
        return builder.build().create(InstagramService.class);
    }
}
