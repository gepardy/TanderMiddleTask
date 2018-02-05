package com.avakimov.tandermiddletask.ui;

import android.arch.paging.PagedListAdapter;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avakimov.tandermiddletask.R;
import com.avakimov.tandermiddletask.domain.Media;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Andrew on 04.02.2018.
 */

public class MediaAdapter extends PagedListAdapter<Media, MediaAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();

    public MediaAdapter(){
        super(Media.DIFF_CALLBACK);
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
        SimpleDraweeView authorAvatar;
        SimpleDraweeView postImage;

        ViewHolder(View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.author_name);
            postDescription = itemView.findViewById(R.id.post_description);
            likesCount = itemView.findViewById(R.id.likes_count);
            authorAvatar = itemView.findViewById(R.id.autor_avatar);
            postImage = itemView.findViewById(R.id.post_image);
        }

        void bindTo(Media media){
            authorName.setText(media.getAuthorName());
            postDescription.setText(media.getCaption());
            likesCount.setText(String.valueOf(media.getLikesCount()));

            Uri imageUri = Uri.parse(media.getStandardResolutionImageUrl());
            postImage.setImageURI(imageUri);

            Uri autrorAvatarUri = Uri.parse(media.getAuthorAvatarUrl());
            authorAvatar.setImageURI(autrorAvatarUri);

        }
    }
}
