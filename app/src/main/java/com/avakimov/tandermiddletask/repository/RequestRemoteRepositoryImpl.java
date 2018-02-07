package com.avakimov.tandermiddletask.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.avakimov.tandermiddletask.api.InstagramService;
import com.avakimov.tandermiddletask.api.UserResponse;
import com.avakimov.tandermiddletask.domain.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Andrew on 01.02.2018.
 */

public class RequestRemoteRepositoryImpl implements RequestRemoteRepository {
    private final String TAG = getClass().getName();
    private String token;
    private final InstagramService service;

    public RequestRemoteRepositoryImpl(InstagramService service, String token){
        this.service = service;
        this.token = token;
    }

    @Override
    public LiveData<List<User>> getUsersByNamePart(String value) {
        MutableLiveData<List<User>> result = new MutableLiveData<>();
        // Здесь по-хорошему нужно обращение к другому методу API, но из-за ограничений песочницы,
        // только этот, который не принимает имя пользователя
        service.getUser(token).enqueue(new Callback<UserResponse>() {

            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Log.d(TAG, "Got response!: " + response.body().user);
                    // Моделируем поведение целевого сервиса
                    // возвращаем данные только если они правда похожи на то что ввел пользовтаель
                    if (response.body().user.toString().startsWith(value)) {
                        List<User> resultData = new ArrayList<>(1);
                        resultData.add(response.body().user);
                        result.postValue(resultData);
                    }
                } else {
                    Log.e(TAG, "Unsuccsessful request. Response code is " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                Log.e(TAG, errorMessage);
            }
        });
        return result;
    }

    @Override
    public void setCustomToken(String token) {
        this.token = token;
    }
}
