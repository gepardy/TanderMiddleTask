package com.avakimov.tandermiddletask.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import android.arch.paging.DataSource;

import com.avakimov.tandermiddletask.domain.Media;

import java.util.List;

/**
 * Created by Andrew on 31.01.2018.
 */
@Dao
public interface TanderMiddleDAO {
    @Query("SELECT * FROM media WHERE user_id = :user_id ORDER BY id DESC")
    DataSource.Factory<Integer, MediaEntity> getUsersMedia(int user_id);

    @Insert
    void insertMediaList(List<MediaEntity> mediaList);

    @Query("DELETE FROM media")
    void clearMedia();
}
