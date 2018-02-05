package com.avakimov.tandermiddletask.api;

import android.provider.MediaStore;

import com.avakimov.tandermiddletask.domain.Media;
import com.avakimov.tandermiddletask.domain.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Andrew on 31.01.2018.
 */

public interface InstagramService {
    @GET("users/self")
    Call<UserResponse> getUser(@Query("access_token") String token);
    @GET("users/{user_id}/media/recent")
    Call<MediaResponse> getUserMedia(@Path("user_id") Integer user_id,
                                     @Query("access_token") String token,
                                     @QueryMap Map<String, String> options);
}
