package com.avakimov.tandermiddletask.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

/**
 * Created by Andrew on 04.02.2018.
 */

@Entity(tableName = "media")
public class MediaEntity {
    @PrimaryKey
    private Long     id;
    private Long     user_id;
    private String  standartResolutionImageUrl;
    private String  lowResolutionImageUrl;
    private String  thumbnailImageUrl;
    private String  authorAvatarUrl;
    private String  authorName;
    private int     likesCount;
    private String  caption;

    public static final DiffCallback<MediaEntity> DIFF_CALLBACK = new DiffCallback<MediaEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull MediaEntity oldItem, @NonNull MediaEntity newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull MediaEntity oldItem, @NonNull MediaEntity newItem) {
            return oldItem.getStandartResolutionImageUrl().equals(newItem.getStandartResolutionImageUrl());
        }
    };

    public MediaEntity(Long id, Long user_id, String standartResolutionImageUrl, String lowResolutionImageUrl, String thumbnailImageUrl, String authorAvatarUrl, String authorName, int likesCount, String caption) {
        this.id = id;
        this.user_id = user_id;
        this.standartResolutionImageUrl = standartResolutionImageUrl;
        this.lowResolutionImageUrl = lowResolutionImageUrl;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.authorAvatarUrl = authorAvatarUrl;
        this.authorName = authorName;
        this.likesCount = likesCount;
        this.caption = caption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getStandartResolutionImageUrl() {
        return standartResolutionImageUrl;
    }

    public void setStandartResolutionImageUrl(String standartResolutionImageUrl) {
        this.standartResolutionImageUrl = standartResolutionImageUrl;
    }

    public String getLowResolutionImageUrl() {
        return lowResolutionImageUrl;
    }

    public void setLowResolutionImageUrl(String lowResolutionImageUrl) {
        this.lowResolutionImageUrl = lowResolutionImageUrl;
    }

    public String getThumbnailImageUrl() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageUrl(String thumbnailImageUrl) {
        this.thumbnailImageUrl = thumbnailImageUrl;
    }

    public String getAuthorAvatarUrl() {
        return authorAvatarUrl;
    }

    public void setAuthorAvatarUrl(String authorAvatarUrl) {
        this.authorAvatarUrl = authorAvatarUrl;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
