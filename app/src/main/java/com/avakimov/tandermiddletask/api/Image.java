package com.avakimov.tandermiddletask.api;

import android.arch.persistence.room.Embedded;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrew on 03.02.2018.
 */

public class Image {
    @SerializedName("low_resolution")
    public ImageInfo lowResolution;
    public ImageInfo thumbnail;
    @SerializedName("standard_resolution")
    public ImageInfo standartResolution;

}
