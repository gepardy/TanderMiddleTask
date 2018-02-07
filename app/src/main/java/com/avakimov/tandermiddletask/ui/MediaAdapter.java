package com.avakimov.tandermiddletask.ui;

import android.arch.paging.PagedListAdapter;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avakimov.tandermiddletask.R;
import com.avakimov.tandermiddletask.domain.Media;
import com.avakimov.tandermiddletask.util.NetworkState;
import com.avakimov.tandermiddletask.util.Status;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Andrew on 04.02.2018.
 */

public class MediaAdapter extends PagedListAdapter<Media, RecyclerView.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private NetworkState networkState;

    public static final int CONTENT_VIEW_TYPE = R.layout.media_list_item_layout;
    public static final int PROGRESS_VIEW_TYPE = R.layout.progress_item;

    public MediaAdapter(){
        super(Media.DIFF_CALLBACK);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "OnCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == CONTENT_VIEW_TYPE) {
            view = layoutInflater.inflate(CONTENT_VIEW_TYPE, parent, false);
            return new MediaItemViewHolder(view);
        } else if (viewType == PROGRESS_VIEW_TYPE) {
            view = layoutInflater.inflate(PROGRESS_VIEW_TYPE, parent, false);
            return new ProgressItemViewHolder(view);
        } else {
            throw new IllegalArgumentException("View type" + viewType + "not supported");
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "OnBindViewHolder. Position: " + position);
        switch (getItemViewType(position)) {
            case CONTENT_VIEW_TYPE:
                ((MediaItemViewHolder) holder).bind(getItem(position));
                break;
            case PROGRESS_VIEW_TYPE:
                ((ProgressItemViewHolder) holder).bind(networkState);
        }
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "onGetItemViewType. Position:" + position + ". Item count: " + getItemCount() + ". Has extra row?: " + hasExtraRow());
        return hasExtraRow() && position == getItemCount() - 1 ? PROGRESS_VIEW_TYPE : CONTENT_VIEW_TYPE;
    }

    public void setNetworkState(NetworkState networkState) {
        Log.d(TAG, "Network state changed");
        NetworkState previousNetworkState = this.networkState;
        boolean previousHasExtraRow = hasExtraRow();
        this.networkState = networkState;
        boolean hasExtraRow = hasExtraRow();
        if (previousHasExtraRow != hasExtraRow) {
            if (previousHasExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if ( hasExtraRow && previousNetworkState != networkState) {
            notifyItemChanged(getItemCount() - 1);
        }

    }

    static class MediaItemViewHolder extends RecyclerView.ViewHolder{
        TextView authorName;
        TextView postDescription;
        TextView likesCount;
        SimpleDraweeView authorAvatar;
        SimpleDraweeView postImage;

        MediaItemViewHolder(View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.author_name);
            postDescription = itemView.findViewById(R.id.post_description);
            likesCount = itemView.findViewById(R.id.likes_count);
            authorAvatar = itemView.findViewById(R.id.autor_avatar);
            postImage = itemView.findViewById(R.id.post_image);
        }

        void bind(Media media){
            authorName.setText(media.getAuthorName());
            postDescription.setText(media.getCaption());
            likesCount.setText(String.valueOf(media.getLikesCount()));

            Uri imageUri = Uri.parse(media.getStandardResolutionImageUrl());
            postImage.setImageURI(imageUri);

            Uri autrorAvatarUri = Uri.parse(media.getAuthorAvatarUrl());
            authorAvatar.setImageURI(autrorAvatarUri);

        }
    }

    static class ProgressItemViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private TextView errMessage;
        private Button btn;

        ProgressItemViewHolder(View itemView) {
            super(itemView);
            this.progressBar = itemView.findViewById(R.id.progressBar2);
            this.errMessage = itemView.findViewById(R.id.err_message);
            this.btn = itemView.findViewById(R.id.retry_button);
        }

        void bind(NetworkState networkState) {
            if (networkState != null && networkState == NetworkState.LOADING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == Status.FAILED) {
                errMessage.setVisibility(View.VISIBLE);
                errMessage.setText(networkState.getMessage());
                btn.setVisibility(View.VISIBLE);
            } else {
                errMessage.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);
            }
        }

    }
}
