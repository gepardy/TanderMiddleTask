package com.avakimov.tandermiddletask.di;

import com.avakimov.tandermiddletask.MediaListActivity;

import dagger.Subcomponent;

/**
 * Created by Andrew on 04.02.2018.
 */

@MediaListScope
@Subcomponent(modules = {MediaListModule.class})
public interface MediaListComponent {
    void inject(MediaListActivity activity);
}
