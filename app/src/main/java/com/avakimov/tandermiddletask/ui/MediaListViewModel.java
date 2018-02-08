package com.avakimov.tandermiddletask.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.avakimov.tandermiddletask.api.MediaResponse;
import com.avakimov.tandermiddletask.domain.Media;
import com.avakimov.tandermiddletask.repository.MediaListLocalRepository;
import com.avakimov.tandermiddletask.repository.MediaListRemoteRepository;
import com.avakimov.tandermiddletask.util.NetworkState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 03.02.2018.
 */

public class MediaListViewModel extends ViewModel {
    private final String TAG = getClass().getSimpleName();

    private MediaListRemoteRepository remoteRepository;
    private MediaListLocalRepository localRepository;
    private Long userId;
    private Long lastMediaId;
    private String userNameRequest;

    private LiveData<PagedList<Media>> mediaList;
    private LiveData<NetworkState> networkState;

    private MutableLiveData<String> findUserTrigger;
    private LiveData<Long> findMediaTrigger;

    private static final int LOCAL_LIST_PAGE_SIZE = 6;

    public MediaListViewModel(MediaListRemoteRepository remoteRepository, MediaListLocalRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
        networkState = remoteRepository.getNetworkState();
        findUserTrigger = new MutableLiveData<>();

    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Media>> getMediaListByUserName(String userName) {
        this.userNameRequest = userName;
        findMediaTrigger = Transformations.switchMap(findUserTrigger, input ->
                remoteRepository.getExactUserByName(input));
        initMediaList();
        findUserTrigger.postValue(userName);
        return mediaList;
    }

    public LiveData<PagedList<Media>> getMediaListByUserId(Long user_id) {
        findMediaTrigger = new MutableLiveData<>();
        initMediaList();
        ((MutableLiveData<Long>) findMediaTrigger).postValue(user_id);
        return mediaList;
    }

    private void initMediaList(){
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(LOCAL_LIST_PAGE_SIZE)
                .build();

        mediaList = Transformations.switchMap( findMediaTrigger, input -> {
            userId = input;
            return new LivePagedListBuilder<>(localRepository.getMediaByUserId(input), config)
                    .setBoundaryCallback(new MediaListBoundaryCallback(input))
                    .build();
        });
    }

    public void clearMedia() {
        if (userId != null) {
            localRepository.clearMedia(userId);
        }
    }

    public void setCustomToken(String customToken) {
        remoteRepository.setCustomToken(customToken);
    }

    public void retryLoad(){
        if (userId != null) {
            loadSomeMedia(lastMediaId);
        } else if (userNameRequest != null ) {
            findUserTrigger.postValue(userNameRequest);
        }
    }

    private void loadSomeMedia(Long last_id) {
        this.lastMediaId = last_id;
        remoteRepository.requestMediaList(userId, last_id, (List<MediaResponse.InstaMedia> instaMediaList) -> {
            List<Media> mediaList = new ArrayList<>(instaMediaList.size());
            for ( MediaResponse.InstaMedia m : instaMediaList) {
                Log.d(TAG, "Processing media record with id:" + Long.valueOf(m.id.substring(0, m.id.indexOf("_"))));
                mediaList.add( new Media( Long.valueOf(m.id.substring(0, m.id.indexOf("_"))),
                        m.user.id,
                        m.images.standardResolution.url,
                        m.images.lowResolution.url,
                        m.images.thumbnail.url,
                        m.user.profileImageUrl,
                        m.user.username,
                        m.likes.count,
                        m.caption != null ? m.caption.text : null));
            }
            localRepository.insertMedia(mediaList);
        });

    }

    //  Класс для информирования ViewModel о том что лист дошел до границы,
    // и пора бы скачать данные из сети
    private class MediaListBoundaryCallback extends PagedList.BoundaryCallback<Media> {
        private String TAG = getClass().getSimpleName();
        private Long user_id;

        MediaListBoundaryCallback(Long user_id) {
            this.user_id = user_id;
        }

        @Override
        public void onZeroItemsLoaded() {
            Log.d(TAG, "Zero items loaded");
            loadSomeMedia(null);
        }

        @Override
        public void onItemAtFrontLoaded(@NonNull Media itemAtFront) {}

        @Override
        public void onItemAtEndLoaded(@NonNull Media itemAtEnd) {
            Log.d(TAG, "End item loaded " + itemAtEnd.getId());
            loadSomeMedia(itemAtEnd.getId());
        }


    }

}
