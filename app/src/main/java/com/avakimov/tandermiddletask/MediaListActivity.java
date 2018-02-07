package com.avakimov.tandermiddletask;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avakimov.tandermiddletask.di.MediaListModule;
import com.avakimov.tandermiddletask.ui.MediaAdapter;
import com.avakimov.tandermiddletask.ui.MediaListViewModel;
import com.avakimov.tandermiddletask.util.MediaListViewModelFactory;

import javax.inject.Inject;

public class MediaListActivity extends AppCompatActivity {
    @Inject
    MediaListViewModelFactory mediaListViewModelFactory;

    RecyclerView recyclerView;
    MediaListViewModel viewModel;

    public static final String SEARCH_TYPE_TAG = "search_type";
    public static final String EXTRA_USER_NAME = "user_name";
    public static final String EXTRA_USER_ID = "user_id";

    public static final int SEARCH_TYPE_BY_NAME = 1;
    public static final int SEARCH_TYPE_BY_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_list);

        recyclerView = findViewById(R.id.recyclerView);

        App.getComponent(getApplicationContext()).getMediaListComponent(new MediaListModule()).inject(this);

        viewModel = ViewModelProviders
                .of(this, mediaListViewModelFactory)
                .get(MediaListViewModel.class);

        final MediaAdapter mediaAdapter = new MediaAdapter();
        viewModel.getNetworkState().observe(this, mediaAdapter::setNetworkState);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mediaAdapter.getItemViewType(position)) {
                    case MediaAdapter.CONTENT_VIEW_TYPE:
                        return 1;
                    case MediaAdapter.PROGRESS_VIEW_TYPE:
                        return 2;
                    default:
                        return 0;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mediaAdapter);

        Intent intent = getIntent();
        int searchType = intent.getIntExtra(SEARCH_TYPE_TAG, 1);

        if (searchType == SEARCH_TYPE_BY_NAME) {

            String user_name = intent.getStringExtra(EXTRA_USER_NAME);
            if (user_name != null) {
                viewModel.getMediaListByUserName(user_name)
                        .observe(this, mediaAdapter::setList);
            }
        } else if (searchType == SEARCH_TYPE_BY_ID) {

            int user_id = intent.getIntExtra(EXTRA_USER_ID, -1);
            if (user_id != -1) {
                viewModel.getMediaListByUserId(intent.getIntExtra(EXTRA_USER_ID, 0))
                        .observe(this, mediaAdapter::setList);
            }
        }

    }
}
