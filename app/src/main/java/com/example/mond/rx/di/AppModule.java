package com.example.mond.rx.di;

import android.content.Context;

import com.example.mond.rx.app.App;
import com.example.mond.rx.BuildConfig;
import com.example.mond.rx.data.repository.ProductRepositoryImpl;
import com.example.mond.rx.domain.ProductsRepository;
import com.example.mond.rx.domain.StoreRepository;
import com.example.mond.rx.data.repository.StoreRepositoryImpl;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

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
                .baseUrl(BuildConfig.LCBO_WEB)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    Context providesContext() {
        return app.getApplicationContext();
    }

    @Provides
    ProductsRepository providesRepositoryRepository() {
        return new ProductRepositoryImpl();
    }

    @Provides
    StoreRepository providesStoreRepository() {
        return new StoreRepositoryImpl();
    }
}
