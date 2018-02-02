package com.avakimov.tandermiddletask.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.avakimov.tandermiddletask.domain.User;
import com.avakimov.tandermiddletask.repository.RemoteRepository;
import com.avakimov.tandermiddletask.repository.RequestRepository;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * Created by Andrew on 31.01.2018.
 */

public class RequestViewModel extends ViewModel {
    private RequestRepository localRepository;
    private RemoteRepository remoteRepository;
    private LiveData<List<User>> suggestions;
    private MutableLiveData<String> requestTrigger;

    public RequestViewModel(RequestRepository local, RemoteRepository remote) {
        this.localRepository = local;
        this.remoteRepository = remote;

        requestTrigger = new MutableLiveData<>();
        suggestions = Transformations.switchMap(requestTrigger,
                input -> remoteRepository.getUsersByNamePart(input));
    }

    public void onTypeInSearchField(String value) {
        if (value.length() > 2) {
            requestTrigger.postValue(value);
        }
    }

    public LiveData<List<User>> getSuggestions() {
        return suggestions;
    }
}
