package com.avakimov.tandermiddletask.di;

import com.avakimov.tandermiddletask.RequestActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Andrew on 02.02.2018.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    RequestComponent getRequestComponent(RequestModule requestModule);
    MediaListComponent getMediaListComponent(MediaListModule mediaListModule);
}
