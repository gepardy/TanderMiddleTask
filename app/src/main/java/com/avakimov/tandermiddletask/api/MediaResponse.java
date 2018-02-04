package com.avakimov.tandermiddletask.api;

import com.avakimov.tandermiddletask.domain.Media;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Andrew on 04.02.2018.
 */

public class MediaResponse {
    @SerializedName("data")
    public List<Media> mediaList;
}
