package com.avakimov.tandermiddletask.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.avakimov.tandermiddletask.domain.Media;
import com.avakimov.tandermiddletask.util.NetworkState;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by Andrew on 03.02.2018.
 */

public interface MediaListRemoteRepository {
    void requestMediaList(Integer user_id, @Nullable Long last_id, MediaListConsumer consumer);
    LiveData<Integer> getExactUserByName(String name);
    LiveData<NetworkState> getNetworkState();

    public interface MediaListConsumer{
        void consumeMedia(List<Media> mediaList);
    }
}
