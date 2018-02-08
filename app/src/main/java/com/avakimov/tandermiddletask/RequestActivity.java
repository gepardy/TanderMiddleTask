package com.avakimov.tandermiddletask;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.avakimov.tandermiddletask.di.RequestModule;
import com.avakimov.tandermiddletask.domain.User;
import com.avakimov.tandermiddletask.ui.RequestViewModel;
import com.avakimov.tandermiddletask.util.InstagramOAuthHelper;
import com.avakimov.tandermiddletask.util.RequestViewModelFactory;

import javax.inject.Inject;

public class RequestActivity extends AppCompatActivity {
    private static final int REQUEST_INTERNET_CODE = 1;
    @Inject
    RequestViewModelFactory requestViewModelFactory;
    @Inject
    InstagramOAuthHelper authHelper;

    private String TAG = getClass().getName();
    private RequestViewModel viewModel;
    private AutoCompleteTextView fieldSearch;
    private Button buttonSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        App.getComponent(getApplicationContext())
                .getRequestComponent(new RequestModule( this )).inject(this);

        setTitle(R.string.request_activity_title);
        fieldSearch = findViewById(R.id.search_field);
        buttonSearch = findViewById(R.id.search_button);

        setObservers();

    }

    private void setObservers() {

        if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.INTERNET}, REQUEST_INTERNET_CODE);
        } else {

            viewModel = ViewModelProviders.of(this, requestViewModelFactory)
                    .get(RequestViewModel.class);

            viewModel.getSuggestions().observe(this, suggestions -> {
                if (suggestions != null) {
                    ArrayAdapter<User> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_dropdown_item_1line,
                            suggestions);
                    fieldSearch.setAdapter(adapter);
                }
            });

            fieldSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    viewModel.onTypeInSearchField(editable.toString());
                }
            });

            buttonSearch.setOnClickListener(view -> searchByName(fieldSearch.getText().toString()));

            fieldSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    searchByName(textView.getText().toString());
                    return true;
                }
                return false;
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_INTERNET_CODE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setObservers();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.request_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.authorize_user:
                authHelper.authorize(token -> {
                    App.setCustomToken(this, token);
                    viewModel.onSetCustomToken( token );
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchByName(String userName){
        Intent intent = new Intent(this, MediaListActivity.class);
        intent.putExtra("search_type", MediaListActivity.SEARCH_TYPE_BY_NAME);
        intent.putExtra("user_name", userName);
        startActivity(intent);
    }


}

