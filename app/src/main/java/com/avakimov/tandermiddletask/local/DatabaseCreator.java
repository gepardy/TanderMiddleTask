package com.avakimov.tandermiddletask.local;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by Andrew on 31.01.2018.
 */


public class DatabaseCreator {
    private static TanderMiddleDatabase database;
    private static final Object lock = new Object();

    public synchronized static TanderMiddleDatabase getBudgetKeeperDatabase(Context context){
        if (database == null) {
            synchronized (lock) {
                if (database == null) {
                    database = Room.databaseBuilder(context,
                            TanderMiddleDatabase.class,
                            "tander_middle").build();
                }
            }
        }
        return database;
    }
}