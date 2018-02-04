package com.avakimov.tandermiddletask.di;

import android.content.Context;

import com.avakimov.tandermiddletask.api.InstagramService;
import com.avakimov.tandermiddletask.local.TanderMiddleDAO;
import com.avakimov.tandermiddletask.repository.MediaListLocalRepository;
import com.avakimov.tandermiddletask.repository.MediaListLocalRepositoryImpl;
import com.avakimov.tandermiddletask.repository.MediaListRemoteRepository;
import com.avakimov.tandermiddletask.repository.MediaListRemoteRepositoryImpl;

import java.util.concurrent.Executor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andrew on 04.02.2018.
 */

@Module
public class MediaListModule {

    @MediaListScope
    @Provides
    public MediaListRemoteRepository provideRemoteRepository( @Named("token") String token, InstagramService service ) {
        return new MediaListRemoteRepositoryImpl(token,service);
    }

    @MediaListScope
    @Provides
    public MediaListLocalRepository provideLocalRepository(TanderMiddleDAO dao, Executor executor) {
        return new MediaListLocalRepositoryImpl(dao, executor);
    }


}
