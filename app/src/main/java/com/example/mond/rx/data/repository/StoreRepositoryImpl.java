package com.example.mond.rx.data.repository;

import com.example.mond.rx.BuildConfig;
import com.example.mond.rx.data.api.LcboAPI;
import com.example.mond.rx.data.mappers.StoreMapper;
import com.example.mond.rx.domain.StoreRepository;
import com.example.mond.rx.domain.models.Store;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class StoreRepositoryImpl implements StoreRepository {

    private final StoreMapper mMapper;
    private Retrofit mRetrofit;

    public StoreRepositoryImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
        mMapper = new StoreMapper();
    }

    @Override
    public Observable<List<Store>> getDataByFilter(int page) {
        LcboAPI api = mRetrofit.create(LcboAPI.class);
        // TODO: 01/08/17  BuildConfig.LCBO_KEY is static for entire project no need to send it each time
        return api.getStores(page, BuildConfig.LCBO_KEY)
                .map(stores -> stores.getResult())
                .map(mMapper);
    }
}
