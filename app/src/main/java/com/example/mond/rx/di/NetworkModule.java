package com.example.mond.rx.di;

import com.example.mond.rx.BuildConfig;
import com.example.mond.rx.data.api.LcboAPI;
import com.example.mond.rx.data.repository.ProductRepositoryImpl;
import com.example.mond.rx.data.repository.StoreRepositoryImpl;
import com.example.mond.rx.domain.ProductsRepository;
import com.example.mond.rx.domain.StoreRepository;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
//    @Named("lcboAPI")
    Retrofit providesLcboApiRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.LCBO_WEB)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    LcboAPI providesLcboAPI(Retrofit retrofit){
        return retrofit.create(LcboAPI.class);
    }

    @Provides
    @Singleton
    ProductsRepository providesProductRepository() {
        return new ProductRepositoryImpl(providesLcboAPI(providesLcboApiRetrofit()));
    }

    @Provides
    @Singleton
    StoreRepository providesStoreRepository() {
        return new StoreRepositoryImpl(providesLcboAPI(providesLcboApiRetrofit()));
    }
}
