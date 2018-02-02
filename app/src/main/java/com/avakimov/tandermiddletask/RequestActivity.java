package com.avakimov.tandermiddletask;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.avakimov.tandermiddletask.domain.User;
import com.avakimov.tandermiddletask.ui.RequestViewModel;
import com.avakimov.tandermiddletask.util.RequestViewModelFactory;

import javax.inject.Inject;

public class RequestActivity extends AppCompatActivity {
    @Inject
    RequestViewModelFactory requestViewModelFactory;

    private String TAG = getClass().getName();
    private RequestViewModel viewModel;
    private AutoCompleteTextView fieldSearch;
    private Button buttonSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

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

        fieldSearch.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            Log.d(TAG, "GetOnEditAction. TextView has:" + textView.getText() + "Action is:" + actionId + "Key event is: " + keyEvent);
            if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
                viewModel.onTypeInSearchField((String) textView.getText());
                return false;
            }
            return false;
        });

        buttonSearch.setOnClickListener(view -> {
            Toast.makeText(this, "Кнопку нажал", Toast.LENGTH_SHORT).show();
        });
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

            new InstagramOAuthService(this)
                    .setClientInfoParameters(clientInfoParams)
                    .authorize(new InstagramOAuthService.OAuthCallBack() {
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
