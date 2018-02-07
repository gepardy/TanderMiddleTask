package com.avakimov.tandermiddletask;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.avakimov.tandermiddletask.di.MediaListModule;
import com.avakimov.tandermiddletask.ui.MediaAdapter;
import com.avakimov.tandermiddletask.ui.MediaListViewModel;
import com.avakimov.tandermiddletask.util.MediaListViewModelFactory;

import javax.inject.Inject;

public class MediaListActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @Inject
    MediaListViewModelFactory mediaListViewModelFactory;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
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
        App.getComponent(getApplicationContext()).getMediaListComponent(new MediaListModule()).inject(this);

        // Настраиваем VM
        viewModel = ViewModelProviders
                .of(this, mediaListViewModelFactory)
                .get(MediaListViewModel.class);

        String customToken = App.getCustomToken(this);
        if (customToken != null) {
            viewModel.setCustomToken(customToken);
        }

        // Выставляем компоненты
        swipeRefreshLayout = findViewById(R.id.content_refresh);
        recyclerView = findViewById(R.id.recyclerView);

        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException exception) {
            Log.d(TAG, exception.getMessage());
        }

        final MediaAdapter mediaAdapter = new MediaAdapter();
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

        viewModel.getNetworkState().observe(this, mediaAdapter::setNetworkState);
        recyclerView.setAdapter(mediaAdapter);

        // Прежде чем начать загрузку еще подпишем swipeRefreshLayout на действие обновления
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Log.d(TAG, "Refresh by swipe");
            swipeRefreshLayout.setRefreshing(true);
            viewModel.clearMedia();
            swipeRefreshLayout.setRefreshing(false);
        });

        Intent intent = getIntent();
        int searchType = intent.getIntExtra(SEARCH_TYPE_TAG, 1);
        switch (searchType) {
            case SEARCH_TYPE_BY_NAME:
                String searchUserName = intent.getStringExtra(EXTRA_USER_NAME);
                if (searchUserName != null && !searchUserName.equals("")) {
                    viewModel.getMediaListByUserName(searchUserName)
                            .observe(this, mediaAdapter::setList);
                } else {
                    showErrorScreen(getString(R.string.empty_name));
                }
                break;

            case SEARCH_TYPE_BY_ID:
                int searchUserId = intent.getIntExtra(EXTRA_USER_ID, -1);
                if (searchUserId != -1) {
                    viewModel.getMediaListByUserId(searchUserId)
                            .observe(this, mediaAdapter::setList);
                }
                break;

            default:
                showErrorScreen(getString(R.string.wrong_search_type));
        }

    }


    private void showErrorScreen(String errorMessage) {

    }
}
