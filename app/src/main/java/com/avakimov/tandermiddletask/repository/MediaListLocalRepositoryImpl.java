package com.avakimov.tandermiddletask.repository;

import android.arch.paging.DataSource;

import com.avakimov.tandermiddletask.domain.Media;
import com.avakimov.tandermiddletask.local.TanderMiddleDAO;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Andrew on 03.02.2018.
 */

public class MediaListLocalRepositoryImpl implements MediaListLocalRepository {
    private TanderMiddleDAO db;
    private Executor executor;

    public MediaListLocalRepositoryImpl(TanderMiddleDAO db, Executor executor){
        this.db = db;
        this.executor = executor;
    }

    @Override
    public DataSource.Factory<Integer, Media> getMediaByUserId(Long user_id) {
        return db.getUsersMedia(user_id);
    }

    @Override
    public void insertMedia(List<Media> mediaList) {
        executor.execute(() -> db.insertMediaList(mediaList));
    }

    @Override
    public void clearMedia(Long user_id) {
        executor.execute(() -> db.clearMedia(user_id));
    }
}
