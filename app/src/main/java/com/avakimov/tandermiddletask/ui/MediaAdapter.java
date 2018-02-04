package com.avakimov.tandermiddletask.ui;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avakimov.tandermiddletask.R;
import com.avakimov.tandermiddletask.local.MediaEntity;

/**
 * Created by Andrew on 04.02.2018.
 */

public class MediaAdapter extends PagedListAdapter<MediaEntity, MediaAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();

    public MediaAdapter(){
        super(MediaEntity.DIFF_CALLBACK);
    }

    @Override
    public MediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.media_list_item_layout, parent, false);
        return new MediaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView authorName;
        TextView postDescription;
        TextView likesCount;
        ImageView authorAvatar;
        ImageView postImage;

        ViewHolder(View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.author_name);
            postDescription = itemView.findViewById(R.id.post_description);
            likesCount = itemView.findViewById(R.id.likes_count);
            authorAvatar = itemView.findViewById(R.id.autor_avatar);
            postImage = itemView.findViewById(R.id.post_image);
        }

        void bindTo(MediaEntity media){
            authorName.setText(media.getAuthorName());
            postDescription.setText(media.getCaption());
            likesCount.setText(media.getLikesCount());
        }
    }
}
