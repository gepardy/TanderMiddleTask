package com.avakimov.tandermiddletask.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;
import android.util.Log;

import com.avakimov.tandermiddletask.api.InstagramService;
import com.avakimov.tandermiddletask.api.MediaResponse;
import com.avakimov.tandermiddletask.api.UserResponse;
import com.avakimov.tandermiddletask.util.NetworkState;
import com.avakimov.tandermiddletask.util.Status;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrew on 03.02.2018.
 */

public class MediaListRemoteRepositoryImpl implements MediaListRemoteRepository {
    private String TAG = getClass().getSimpleName();
    private final String token;
    private InstagramService service;
    private MutableLiveData<NetworkState> networkState;

    public MediaListRemoteRepositoryImpl(String token, InstagramService service){
        this.token = token;
        this.service = service;

        networkState = new MutableLiveData<>();
    }

    @Override
    public void requestMediaList(Integer user_id, @Nullable Long last_id, MediaListRemoteRepository.MediaListConsumer consumer) {
        HashMap<String, String> params = new HashMap<>();
        params.put("count", String.valueOf(6));
        if (last_id != null) {
            params.put("max_id", String.valueOf(last_id));
        }
        networkState.postValue(NetworkState.LOADING);
        service.getUserMedia(user_id, token, params).enqueue(new Callback<MediaResponse>() {

            @Override
            public void onResponse(Call<MediaResponse> call, Response<MediaResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    consumer.consumeMedia(response.body().instaMediaList);
                } else {
                    Log.e(TAG, "Request to media failed. Response code is: " + response.code());
                }
                networkState.postValue(NetworkState.LOADED);
            }

            @Override
            public void onFailure(Call<MediaResponse> call, Throwable t) {
                String errorMsg = t.getMessage();
                networkState.postValue(new NetworkState(Status.FAILED, errorMsg));
                Log.e(TAG, errorMsg);
            }
        });
    }

    @Override
    public LiveData<Integer> getExactUserByName(String name) {
        MutableLiveData<Integer> result = new MutableLiveData<>();
        service.getUser(token).enqueue(new Callback<UserResponse>() {
               @Override
               public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        if (response.body().user.username.equals(name)) {
                            result.postValue(response.body().user.id);
                        }
                    }
               }

               @Override
               public void onFailure(Call<UserResponse> call, Throwable t) {
                   Log.e(TAG, t.getMessage());
               }
           }
        );
        return result;
    }

    @Override
    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }


}
