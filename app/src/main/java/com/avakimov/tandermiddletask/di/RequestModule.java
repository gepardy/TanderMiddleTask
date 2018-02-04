package com.avakimov.tandermiddletask.di;

import com.avakimov.tandermiddletask.api.InstagramService;
import com.avakimov.tandermiddletask.repository.RequestRemoteRepository;
import com.avakimov.tandermiddletask.repository.RequestRemoteRepositoryImpl;
import com.avakimov.tandermiddletask.repository.RequestLocalRepository;
import com.avakimov.tandermiddletask.repository.RequestLocalRespoitoryImpl;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andrew on 02.02.2018.
 */
@Module
public class RequestModule {

    @RequestScope
    @Provides
    RequestLocalRepository provideRequestRespoitory(){
        return new RequestLocalRespoitoryImpl();
    }

    @RequestScope
    @Provides
    RequestRemoteRepository provideRemoteRepository(InstagramService instagramService, @Named("token") String token){
        return new RequestRemoteRepositoryImpl(instagramService, token);
    }
}
