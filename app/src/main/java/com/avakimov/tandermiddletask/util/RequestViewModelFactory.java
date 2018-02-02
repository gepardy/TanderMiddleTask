package com.avakimov.tandermiddletask.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.avakimov.tandermiddletask.repository.RemoteRepository;
import com.avakimov.tandermiddletask.repository.RequestRepository;
import com.avakimov.tandermiddletask.ui.RequestViewModel;

import javax.inject.Inject;

/**
 * Created by Andrew on 02.02.2018.
 */

public class RequestViewModelFactory implements ViewModelProvider.Factory {
    private RequestRepository localRepo;
    private RemoteRepository remoteRepo;

    @Inject
    public RequestViewModelFactory(RequestRepository local, RemoteRepository remote) {
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
