package com.avakimov.tandermiddletask.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;
import android.util.Log;

import com.avakimov.tandermiddletask.api.FindUserResponse;
import com.avakimov.tandermiddletask.api.InstagramService;
import com.avakimov.tandermiddletask.api.MediaResponse;
import com.avakimov.tandermiddletask.domain.Media;
import com.avakimov.tandermiddletask.domain.User;
import com.avakimov.tandermiddletask.util.NetworkState;
import com.avakimov.tandermiddletask.util.Status;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

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
    private MutableLiveData<List<Media>> mediaList;
    private MutableLiveData<Integer> userId;

    public MediaListRemoteRepositoryImpl(String token, InstagramService service){
        this.token = token;
        this.service = service;

        networkState = new MutableLiveData<>();
    }

    @Override
    public void requestMediaList(Integer user_id, @Nullable Integer last_id, MediaListRemoteRepository.MediaListConsumer consumer) {
        HashMap<String, String> params = new HashMap<>();
        params.put("count", String.valueOf(5));
        if (last_id != null) {
            params.put("min_id", String.valueOf(last_id));
        }
        networkState.postValue(NetworkState.LOADING);
        service.getUserMedia(token, user_id, params).enqueue(new Callback<MediaResponse>() {

            @Override
            public void onResponse(Call<MediaResponse> call, Response<MediaResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    consumer.consumeMedia(response.body().mediaList);
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
        service.findUsers(token, name).enqueue(new Callback<FindUserResponse>() {
               @Override
               public void onResponse(Call<FindUserResponse> call, Response<FindUserResponse> response) {
                    if (response.isSuccessful() && response.code() == 200) {
                        List<User> responseBody = response.body().data;

                        if (responseBody.size() != 1) {
                            Log.d(TAG, "Response has more than one user");
                            return;
                        }

                        if (!responseBody.get(0).username.equals(name)) {
                            Log.d(TAG, "Specified user is not found");
                            return;
                        }

                        result.postValue(responseBody.get(0).id);
                    }
               }

               @Override
               public void onFailure(Call<FindUserResponse> call, Throwable t) {
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
