package com.example.mond.rx.di;

import com.example.mond.rx.BuildConfig;
import com.example.mond.rx.data.repository.ProductRepositoryImpl;
import com.example.mond.rx.data.repository.StoreRepositoryImpl;
import com.example.mond.rx.domain.ProductsRepository;
import com.example.mond.rx.domain.StoreRepository;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

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
    ProductsRepository providesRepositoryRepository() {
        return new ProductRepositoryImpl(providesLcboApiRetrofit());
    }

    @Provides
    StoreRepository providesStoreRepository() {
        return new StoreRepositoryImpl(providesLcboApiRetrofit());
    }
}
