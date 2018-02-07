package com.avakimov.tandermiddletask.di;

import android.content.Context;

import com.avakimov.tandermiddletask.util.InstagramOAuthHelper;
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
    private final Context context;

    public RequestModule(Context context) {
        this.context = context;
    }

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

    @RequestScope
    @Provides
    InstagramOAuthHelper provideInstagramAuthHelper(@Named("client_id") String clientId, @Named("redirect_uri") String redirectUri) {
        return new InstagramOAuthHelper(context, clientId, redirectUri);
    }

    @RequestScope
    @Provides
    @Named("client_id")
    String provideClientId(){
        return "c4ee1813155a4343b3764bd1ebeeadca";
    }

    @RequestScope
    @Provides
    @Named("client_secret")
    String provigeClientSecret(){
        return "312f1f3b11b24a469edbe55001c66685";
    }

    @RequestScope
    @Provides
    @Named("redirect_uri")
    String provideRedirectUri(){
        return "http://avakimov.am.com";
    }
}
