package com.avakimov.tandermiddletask.util;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avakimov.tandermiddletask.R;


/**
 * Created by Andrew on 31.01.2018.
 */

public class InstagramOAuthHelper {

    public interface OAuthCallback {
        void getToken(String token);
    }

    private static final String AUTHENTICATION_URL = "https://api.instagram.com/oauth/authorize/?";

    private Context context;
    private String clientId;
    private String redirectUri;

    public InstagramOAuthHelper(Context context, String clientId, String redirectUri) {
        this.context = context;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public void authorize( OAuthCallback listener ) {
        CookieManager.getInstance().removeAllCookies(aBoolean -> new InstagramOAuthDialog(context, listener, getAuthenticationApi(), redirectUri).show());
    }

    private String getAuthenticationApi() {
        return (AUTHENTICATION_URL + "client_id=" + clientId + "&redirect_uri=" + redirectUri + "&response_type=token");
    }

    static class InstagramOAuthDialog extends Dialog {

        private ProgressBar progressBar;
        private WebView webView;
        private OAuthCallback listener;

        private String authenticationApi;
        private String redirectUri;

        InstagramOAuthDialog(Context context, OAuthCallback listener, String authenticationApi, String redirectUri) {
            super(context);
            this.authenticationApi = authenticationApi;
            this.redirectUri = redirectUri;

            View view = LayoutInflater.from(context).inflate(R.layout.auth_dialog, null);

            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setCanceledOnTouchOutside(false);
            this.setContentView(view);

            this.webView = view.findViewById(R.id.webView);
            this.progressBar =  view.findViewById(R.id.progressBar);

            this.listener = listener;

        }

        @Override
        public void show() {
            this.webView.getSettings().setJavaScriptEnabled(false);
            this.webView.loadUrl(authenticationApi);
            this.webView.setHorizontalScrollBarEnabled(false);
            this.webView.setVerticalScrollBarEnabled(false);
            this.webView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith(redirectUri)) {
                        String token = url.split("=")[1];
                        listener.getToken(token);
                        InstagramOAuthDialog.this.dismiss();
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });

            this.webView.setWebChromeClient(new WebChromeClient() {

                @Override
                public void onProgressChanged(WebView view, int percent) {
                    if (percent >= 100) {
                        webView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                    super.onProgressChanged(view, percent);
                }
            });

            super.show();
        }
    }


}
