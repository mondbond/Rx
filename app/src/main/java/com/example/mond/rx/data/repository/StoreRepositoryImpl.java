package com.example.mond.rx.data.repository;

import com.example.mond.rx.data.api.LcboAPI;
import com.example.mond.rx.data.mappers.StoreMapper;
import com.example.mond.rx.domain.StoreRepository;
import com.example.mond.rx.domain.models.Store;

import java.util.List;

import io.reactivex.Observable;

public class StoreRepositoryImpl implements StoreRepository {

    private final StoreMapper mMapper;
    private LcboAPI mApi;

    public StoreRepositoryImpl(LcboAPI lcboAPI) {
        mApi = lcboAPI;
        mMapper = new StoreMapper();
    }

    @Override
    public Observable<List<Store>> getDataByFilter(int page) {
        return mApi.getStores(page, LcboAPI.LCBO_TOKEN_KEY)
                .map(stores -> stores.getResult())
                .flatMap(Observable::fromIterable)
                .map(mMapper)
                .toList()
                .toObservable();
    }
}
