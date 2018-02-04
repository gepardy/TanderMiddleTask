package com.avakimov.tandermiddletask.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.avakimov.tandermiddletask.repository.RequestRemoteRepository;
import com.avakimov.tandermiddletask.repository.RequestLocalRepository;
import com.avakimov.tandermiddletask.ui.RequestViewModel;

import javax.inject.Inject;

/**
 * Created by Andrew on 02.02.2018.
 */

public class RequestViewModelFactory implements ViewModelProvider.Factory {
    private RequestLocalRepository localRepo;
    private RequestRemoteRepository remoteRepo;

    @Inject
    public RequestViewModelFactory(RequestLocalRepository local, RequestRemoteRepository remote) {
        localRepo = local;
        remoteRepo = remote;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RequestViewModel.class)) {
            return (T) new RequestViewModel(localRepo, remoteRepo);
        }
        throw new IllegalArgumentException("Wrong Viewmodel class");
    }
}
