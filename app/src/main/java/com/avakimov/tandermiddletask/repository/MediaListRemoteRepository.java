package com.avakimov.tandermiddletask.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.avakimov.tandermiddletask.api.MediaResponse;
import com.avakimov.tandermiddletask.util.NetworkState;

import java.util.List;

/**
 * Created by Andrew on 03.02.2018.
 */

public interface MediaListRemoteRepository {
    void requestMediaList(Long user_id, @Nullable Long last_id, MediaListConsumer consumer);
    LiveData<Long> getExactUserByName(String name);
    LiveData<NetworkState> getNetworkState();

    void setCustomToken(String customToken);

    interface MediaListConsumer{
        void consumeMedia(List<MediaResponse.InstaMedia> instaMediaList);
    }
}
