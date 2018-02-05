package com.avakimov.tandermiddletask.domain;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import com.avakimov.tandermiddletask.api.Image;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrew on 31.01.2018.
 */
public class Media {
    public String id;
    public User user;
    public String link;
    public Image images;
    public Likes likes;
    public Caption caption;

    public class Likes {
        public int count;
    }

    public class Caption {
        public String text;
    }

}
