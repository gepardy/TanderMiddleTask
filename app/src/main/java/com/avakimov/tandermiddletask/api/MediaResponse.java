package com.avakimov.tandermiddletask.api;

import com.avakimov.tandermiddletask.domain.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Andrew on 04.02.2018.
 */

public class MediaResponse {
    @SerializedName("data")
    public List<InstaMedia> instaMediaList;

    public static class InstaMedia {
        public String id;
        public User user;
        public String link;
        public Image images;
        public Likes likes;
        public Caption caption;
    }

    public static class Likes {
        public int count;
    }

    public static class Caption {
        public String text;
    }

    public static class Image {
        @SerializedName("low_resolution")
        public ImageInfo lowResolution;
        public ImageInfo thumbnail;
        @SerializedName("standard_resolution")
        public ImageInfo standardResolution;

    }

    public static class ImageInfo {
        public String url;
        public int width;
        public int height;
    }
}
