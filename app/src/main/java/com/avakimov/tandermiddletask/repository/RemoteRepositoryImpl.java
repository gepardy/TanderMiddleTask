package com.avakimov.tandermiddletask.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.avakimov.tandermiddletask.api.InstagramService;
import com.avakimov.tandermiddletask.domain.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrew on 01.02.2018.
 */

public class RemoteRepositoryImpl implements RemoteRepository {
    private final String TAG = getClass().getName();
    private final String token;
    private final InstagramService service;

    @Inject
    public RemoteRepositoryImpl(InstagramService service, @Named("Token") String token){
        this.service = service;
        this.token = token;
    }

    @Override
    public LiveData<List<User>> getUsersByNamePart(String value) {
        MutableLiveData<List<User>> result = new MutableLiveData<>();
        // Здесь по-хорошему нужно обращение к другому методу API, но из-за ограничений песочницы,
        // только этот
        service.getUser(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    List<User> resultData = new ArrayList<User>(1);
                    resultData.add(response.body());
                    result.postValue(resultData);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                Log.e(TAG, errorMessage);
            }
        });
        return result;
    }
}
