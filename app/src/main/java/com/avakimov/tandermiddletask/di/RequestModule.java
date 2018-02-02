package com.avakimov.tandermiddletask.di;

import com.avakimov.tandermiddletask.api.InstagramService;
import com.avakimov.tandermiddletask.repository.RemoteRepository;
import com.avakimov.tandermiddletask.repository.RemoteRepositoryImpl;
import com.avakimov.tandermiddletask.repository.RequestRepository;
import com.avakimov.tandermiddletask.repository.RequestRespoitoryImpl;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Andrew on 02.02.2018.
 */
@Module
public class RequestModule {

    @Provides
    RequestRepository provideRequestRespoitory(){
        return new RequestRespoitoryImpl();
    }

    @Provides
    RemoteRepository provideRemoteRepository(InstagramService instagramService, @Named("token") String token){
        return new RemoteRepositoryImpl(instagramService, token);
    }
}
