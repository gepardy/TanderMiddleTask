package com.avakimov.tandermiddletask.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.avakimov.tandermiddletask.repository.MediaListLocalRepository;
import com.avakimov.tandermiddletask.repository.MediaListRemoteRepository;
import com.avakimov.tandermiddletask.ui.MediaListViewModel;

import javax.inject.Inject;

/**
 * Created by Andrew on 04.02.2018.
 */

public class MediaListViewModelFactory implements ViewModelProvider.Factory{
    private MediaListRemoteRepository remote;
    private MediaListLocalRepository local;

    @Inject
    public MediaListViewModelFactory(MediaListRemoteRepository remote, MediaListLocalRepository local) {
        this.remote = remote;
        this.local = local;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MediaListViewModel.class)) {
            return (T) new MediaListViewModel(remote, local);
        }
        throw new IllegalArgumentException("Wrong Viewmodel class");
    }
}
