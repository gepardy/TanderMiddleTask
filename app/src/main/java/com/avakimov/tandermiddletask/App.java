package com.avakimov.tandermiddletask;

import android.app.Application;
import android.content.Context;

import com.avakimov.tandermiddletask.di.AppComponent;
import com.avakimov.tandermiddletask.di.AppModule;
import com.avakimov.tandermiddletask.di.DaggerAppComponent;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Andrew on 02.02.2018.
 */

public class App extends Application {
    private AppComponent appComponent;
    private String customToken;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext())).build();

        Fresco.initialize(getApplicationContext());

    }

    public static AppComponent getComponent(Context context){
        return ((App) context.getApplicationContext()).appComponent;
    }

    public static void setCustomToken(Context context, String customToken){
        ((App) context.getApplicationContext()).customToken = customToken;
    }

    public static String getCustomToken(Context context){
        return  ((App) context.getApplicationContext()).customToken;
    }

}
