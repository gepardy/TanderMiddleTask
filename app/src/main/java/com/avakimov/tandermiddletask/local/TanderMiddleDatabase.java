package com.avakimov.tandermiddletask.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.avakimov.tandermiddletask.domain.Media;
import com.avakimov.tandermiddletask.domain.User;

/**
 * Created by Andrew on 31.01.2018.
 */
@Database( entities = {Media.class, User.class},
           version = 3)
public abstract class TanderMiddleDatabase extends RoomDatabase{
    public abstract TanderMiddleDAO getTanderMiddleDao();
}
