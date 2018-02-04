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
    Call<MediaResponse> getUserMedia(@Query("access_token") String token,
                                   @Path("user_id") Integer user_id,
                                   @QueryMap Map<String, String> options);
    @GET("users/search")
    Call<FindUserResponse> findUsers(@Query("access_token") String token,
                                     @Query("q") String query);
}
