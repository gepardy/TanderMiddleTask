package com.avakimov.tandermiddletask.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import android.arch.paging.DataSource;

import com.avakimov.tandermiddletask.domain.Media;

import java.util.List;

/**
 * Created by Andrew on 31.01.2018.
 */
@Dao
public interface TanderMiddleDAO {
    @Query("SELECT * FROM Media WHERE user_id = :user_id ORDER BY id DESC")
    DataSource.Factory<Integer, Media> getUsersMedia(Long user_id);

    @Insert
    void insertMediaList(List<Media> mediaList);

    @Query("DELETE FROM Media WHERE user_id = :user_id")
    void clearMedia(Long user_id);

}
