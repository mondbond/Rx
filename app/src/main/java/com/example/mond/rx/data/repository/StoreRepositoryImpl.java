package com.example.mond.rx.data.repository;

import com.example.mond.rx.BuildConfig;
import com.example.mond.rx.data.mappers.StoreMapper;
import com.example.mond.rx.domain.StoreRepository;
import com.example.mond.rx.domain.models.Store;
import com.example.mond.rx.data.api.LcboAPI;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class StoreRepositoryImpl implements StoreRepository {

    private boolean mIsLastPage;
    private Retrofit mRetrofit;


    public StoreRepositoryImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    // TODO: ? 7/25/17 This is very bad. Look through the samples that were provided in android chat
    // TODO: 7/25/17 Look in the sample apps how repository are implemented - clean and simple.

//    TODO - question. because way of retrofit instance passed or maybe something else too?

    // ("https://github.com/EugeneYovbak/ReactiveApp", "https://Zolotar_Oleg@bitbucket.org/Zolotar_Oleg/hitbtc.git")
    // TODO: ? 7/25/17 Why are you fetching a Call<> and the converting it to Observable<>? Retrofit
    // can retrieve an Observable<>

    // TODO: - 7/25/17 Don't pass a Retrofit instance in here. Look in the sample how REST interface
    // is build. Rewrite this class and the ProductsRepository using the approach from the sample apps

    @Override
    public Observable<List<Store>> getDataByFilter(int page) throws IOException {
        LcboAPI api = mRetrofit.create(LcboAPI.class);
        return api.getStores(page, BuildConfig.LCBO_KEY)
                .map(stores ->stores.getResult())
                .map(new StoreMapper());
    }

//    private Observable<Stores> getStoresByRetrofit(Retrofit retrofit, int page) throws IOException {
//        LcboAPI api = retrofit.create(LcboAPI.class);
//        Observable<Stores> response = null;
//        return api.getStores(page, BuildConfig.LCBO_KEY);
//    }
}
