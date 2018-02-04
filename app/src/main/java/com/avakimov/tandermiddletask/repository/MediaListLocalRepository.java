package com.avakimov.tandermiddletask.repository;

import android.arch.paging.DataSource;

import com.avakimov.tandermiddletask.local.MediaEntity;

import java.util.List;

/**
 * Created by Andrew on 03.02.2018.
 */

public interface MediaListLocalRepository {
    DataSource.Factory<Integer, MediaEntity> getMediaByUserId(Integer userId);
    void insertMedia(List<MediaEntity> mediaList);
}
