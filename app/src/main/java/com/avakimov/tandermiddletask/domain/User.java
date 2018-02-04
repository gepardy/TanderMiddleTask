package com.avakimov.tandermiddletask.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Andrew on 31.01.2018.
 */

@Entity
public class User {
    public String username;

    @PrimaryKey
    public int id;
    public String profileImageUrl;

    @Override
    public String toString() {
        return username;
    }
}
