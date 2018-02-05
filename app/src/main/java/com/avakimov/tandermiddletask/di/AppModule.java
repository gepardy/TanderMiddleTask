package com.avakimov.tandermiddletask.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.avakimov.tandermiddletask.api.InstagramService;
import com.avakimov.tandermiddletask.local.TanderMiddleDAO;
import com.avakimov.tandermiddletask.local.TanderMiddleDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    private static final String BASE_URL = "https://api.instagram.com/v1/";
    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    InstagramService provideInstagramService(Retrofit retrofit){
        return retrofit.create(InstagramService.class);

    }

    @Provides
    @Named("token")
    String provideToken() {
        return "1940222073.c4ee181.00881bce6fc34c69928410a2cb7670b6";
    }

    @Provides
    @Singleton
    TanderMiddleDatabase provideDatabase() {
        return Room.databaseBuilder(context.getApplicationContext(),
                TanderMiddleDatabase.class, "tander_middle_database.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    TanderMiddleDAO provideDao(TanderMiddleDatabase db) {
        return db.getTanderMiddleDao();
    }

    @Provides
    @Singleton
    Executor provideExecutor() {
        return Executors.newFixedThreadPool(5);
    }

}
