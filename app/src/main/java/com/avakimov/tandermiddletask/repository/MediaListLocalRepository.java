package com.avakimov.tandermiddletask.repository;

import android.arch.paging.DataSource;

import com.avakimov.tandermiddletask.domain.Media;

import java.util.List;

/**
 * Created by Andrew on 03.02.2018.
 */

public interface MediaListLocalRepository {
    DataSource.Factory<Integer, Media> getMediaByUserId(Long userId);
    void insertMedia(List<Media> mediaList);
    void clearMedia(Long user_id);
}
