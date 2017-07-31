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

    private Retrofit mRetrofit;

    public StoreRepositoryImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public Observable<List<Store>> getDataByFilter(int page) {
        LcboAPI api = mRetrofit.create(LcboAPI.class);
        return api.getStores(page, BuildConfig.LCBO_KEY)
                .map(stores -> stores.getResult())
                .map(new StoreMapper());
    }
}
