package com.avakimov.tandermiddletask.domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrew on 31.01.2018.
 */

@Entity
public class User {
    public String username;

    @PrimaryKey
    public int id;
    @SerializedName("profile_picture")
    public String profileImageUrl;

    @Override
    public String toString() {
        return username;
    }
}
