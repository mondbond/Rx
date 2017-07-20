package com.example.mond.rx.di;

import android.content.Context;

import com.example.mond.rx.App;
import com.example.mond.rx.data.RepositoryImpl;
import com.example.mond.rx.data.Repository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
//    @Named("lcboAPI")
    Retrofit providesLcboApiRetrofit() {
        return new Retrofit.Builder()
                // TODO: 20/07/17 hide base url api keys in gradle file and use BuildConfig class
                .baseUrl("https://lcboapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    Context providesContext() {
        return app.getApplicationContext();
    }

    @Provides
    Repository providesLcboRepository() {
        return new RepositoryImpl();
    }
}
