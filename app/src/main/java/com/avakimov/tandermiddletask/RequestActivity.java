package com.avakimov.tandermiddletask;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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

        fieldSearch = findViewById(R.id.search_field);
        buttonSearch = findViewById(R.id.search_button);

        viewModel = ViewModelProviders.of(this, requestViewModelFactory)
                .get(RequestViewModel.class);

        viewModel.getSuggestions().observe(this, suggestions -> {
            if (suggestions != null) {
                ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
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
/*

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import java.util.HashMap;

public class RequestActivity extends AppCompatActivity {
    private String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        AutoCompleteTextView fieldSearch = new AutoCompleteTextView(this);

    }

    private void requestInternetPermission() {
        Log.i(TAG, "CAMERA permission has NOT been granted. Requesting permission.");

        // Camera permission has not been granted yet. Request it directly.
        requestPermissions(new String[]{Manifest.permission.INTERNET}, 1);

    }

    private void getFuckingToken(){
        if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            requestInternetPermission();
        } else {
            HashMap<String, String> clientInfoParams = new HashMap<>();
            clientInfoParams.put("client_id", "c4ee1813155a4343b3764bd1ebeeadca");
            clientInfoParams.put("client_secret", "312f1f3b11b24a469edbe55001c66685");
            clientInfoParams.put("grant_type", "authorization_code");
            clientInfoParams.put("redirect_uri", "http://avakimov.am.com");

            new InstagramOAuthHelper(this)
                    .setClientInfoParameters(clientInfoParams)
                    .authorize(new InstagramOAuthHelper.OAuthCallBack() {
                        @Override
                        public void onSuccessAuthenticate(String accessToken) {
                            Log.i("TAG Token", accessToken);
                        }

                        @Override
                        public void onFailureAuthenticate() {
//1940222073.c4ee181.00881bce6fc34c69928410a2cb7670b6
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            Log.i(TAG, "Received response for internet permission request.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed
                Log.i(TAG, "INTERNET permission has now been granted. going deep.");
                getFuckingToken();

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

*/
