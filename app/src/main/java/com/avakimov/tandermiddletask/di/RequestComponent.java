package com.avakimov.tandermiddletask.di;

import com.avakimov.tandermiddletask.RequestActivity;

import dagger.Subcomponent;

/**
 * Created by Andrew on 02.02.2018.
 */

@Subcomponent(modules = { RequestModule.class })
public interface RequestComponent {
    void inject(RequestActivity requestActivity);
}
