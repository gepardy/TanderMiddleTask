package com.avakimov.tandermiddletask.di;

import com.avakimov.tandermiddletask.api.InstagramService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Andrew on 02.02.2018.
 */
@Module
public class AppModule {
    @Provides
    @Singleton
    InstagramService provideInstagramService(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.instagram.com/v1");
        return builder.build().create(InstagramService.class);

    }

    @Provides
    @Named("token")
    String provideToken() {
        return "1940222073.c4ee181.00881bce6fc34c69928410a2cb7670b6";
    }


}
