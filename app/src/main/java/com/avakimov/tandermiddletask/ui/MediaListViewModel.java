package com.avakimov.tandermiddletask.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.avakimov.tandermiddletask.domain.Media;
import com.avakimov.tandermiddletask.local.MediaEntity;
import com.avakimov.tandermiddletask.repository.MediaListLocalRepository;
import com.avakimov.tandermiddletask.repository.MediaListRemoteRepository;
import com.avakimov.tandermiddletask.util.NetworkState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 03.02.2018.
 */

public class MediaListViewModel extends ViewModel {
    private MediaListRemoteRepository remoteRepository;
    private MediaListLocalRepository localRepository;

    private LiveData<PagedList<MediaEntity>> mediaList;
    private LiveData<NetworkState> networkState;

    private MutableLiveData<String> findUserTrigger;
    private LiveData<Integer> findMediaTrigger;

    public MediaListViewModel(MediaListRemoteRepository remoteRepository, MediaListLocalRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
        localRepository.clearMedia();
        findUserTrigger = new MutableLiveData<>();

    }

    public LiveData<PagedList<MediaEntity>> getMediaListByUserName(String userName) {
        findMediaTrigger = Transformations.switchMap(findUserTrigger, input ->
                remoteRepository.getExactUserByName(input));
        initMediaList();
        findUserTrigger.postValue(userName);
        return mediaList;
    }

    public LiveData<PagedList<MediaEntity>> getMediaListByUserId(Integer user_id) {
        findMediaTrigger = new MutableLiveData<>();
        initMediaList();
        ((MutableLiveData<Integer>) findMediaTrigger).postValue(user_id);
        return mediaList;
    }

    private void initMediaList(){
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(6)
                .build();

        mediaList = Transformations.switchMap( findMediaTrigger, input ->
                new LivePagedListBuilder<>(localRepository.getMediaByUserId(input), config)
                        .setBoundaryCallback(new MediaListBoundaryCallback(input))
                        .build());
    }

    //  Класс для информирования ViewModel о том что лист дошел до границы,
    // и пора бы скачать данные из сети
    private class MediaListBoundaryCallback extends PagedList.BoundaryCallback<MediaEntity> {
        private String TAG = getClass().getSimpleName();
        private int user_id;

        MediaListBoundaryCallback(int user_id) {
            this.user_id = user_id;
        }

        @Override
        public void onZeroItemsLoaded() {
            Log.d(TAG, "Zero items loaded");
            loadSomeMedia(null);
        }

        @Override
        public void onItemAtFrontLoaded(@NonNull MediaEntity itemAtFront) {}

        @Override
        public void onItemAtEndLoaded(@NonNull MediaEntity itemAtEnd) {
            Log.d(TAG, "End item loaded " + itemAtEnd.getId());
            loadSomeMedia(itemAtEnd.getId());
        }

        private void loadSomeMedia(Long last_id) {
            remoteRepository.requestMediaList(user_id, last_id, (List<Media> mediaList) -> {
                List<MediaEntity> mediaEntityList = new ArrayList<>(mediaList.size());
                for ( Media m : mediaList ) {
                    Log.d(TAG, "Processing media record with id:" + Long.valueOf(m.id.substring(0, m.id.indexOf("_"))));
                    mediaEntityList.add( new MediaEntity( Long.valueOf(m.id.substring(0, m.id.indexOf("_"))),
                            Long.valueOf(m.user.id),
                            m.images.standartResolution.url,
                            m.images.lowResolution.url,
                            m.images.thumbnail.url,
                            m.user.profileImageUrl,
                            m.user.username,
                            m.likes.count,
                            m.caption.text));
                }
                localRepository.insertMedia(mediaEntityList);
            });

        }
    }

}
