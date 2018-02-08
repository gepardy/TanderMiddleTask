package com.avakimov.tandermiddletask.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.avakimov.tandermiddletask.domain.User;
import com.avakimov.tandermiddletask.repository.RequestRemoteRepository;

import java.util.List;

/**
 * Created by Andrew on 31.01.2018.
 */

public class RequestViewModel extends ViewModel {
    private RequestRemoteRepository requestRemoteRepository;
    private LiveData<List<User>> suggestions;
    private MutableLiveData<String> requestTrigger;

    private static final int USER_SEARCH_THRESHOLD = 1;

    public RequestViewModel(RequestRemoteRepository remote) {
        this.requestRemoteRepository = remote;

        requestTrigger = new MutableLiveData<>();
        suggestions = Transformations.switchMap(requestTrigger,
                input -> requestRemoteRepository.getUsersByNamePart(input));
    }

    public void onTypeInSearchField(String value) {
        if (value.length() > USER_SEARCH_THRESHOLD) {
            requestTrigger.postValue(value);
        }
    }

    public LiveData<List<User>> getSuggestions() {
        return suggestions;
    }

    public void onSetCustomToken( String token) {
        requestRemoteRepository.setCustomToken( token );
    }
}
