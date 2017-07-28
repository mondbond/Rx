package com.example.mond.rx.data.repository;

import android.util.Log;

import com.example.mond.rx.BuildConfig;
import com.example.mond.rx.data.mappers.StoreMapper;
import com.example.mond.rx.domain.StoreRepository;
import com.example.mond.rx.data.filters.StoreFilter;
import com.example.mond.rx.domain.models.Store;
import com.example.mond.rx.data.models.stores.Result;
import com.example.mond.rx.data.models.stores.Stores;
import com.example.mond.rx.data.api.LcboAPI;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import retrofit2.Response;
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
    // TODO: ? 7/25/17 Why are you fetching a Call<> and the converting it to Observable<>? Retrofit can retrieve an Observable<>

    // TODO: - 7/25/17 Don't pass a Retrofit instance in here. Look in the sample how REST interface is build. Rewrite this class and the ProductsRepository using the approach from the sample apps.

    @Override
    public Observable<Store> getDataByFilter(StoreFilter filter) throws IOException {
        if (filter == null) {
            throw new NullPointerException("Filter is null");
        }

        return Observable.create(new ObservableOnSubscribe<Store>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Store> e) throws Exception {
                int page = 1;
                int count = 0;
                boolean isLastPage = false;

                Stores stores;
                List<Result> results;
                while (count < filter.getCount() || isLastPage) {
                    stores = getStoresByRetrofit(mRetrofit, page);
                    if (stores != null) {
                        isLastPage = stores.getStoresPager().isIsFinalPage();
                        results = stores.getResult();
                        Iterator<Result> iterator = stores.getResult().iterator();
                        while (iterator.hasNext()) {
                            if (!filter.isAppropriate(iterator.next()) || count > filter.getCount()) {
                                iterator.remove();
                            } else {
                                ++count;
                            }
                        }
                        Observable.fromIterable(results)
                                .map(new StoreMapper())
                                .subscribe(e::onNext);
                        page++;
                    }
                }
            }
        });
    }

    private Stores getStoresByRetrofit(Retrofit retrofit, int page) throws IOException {
        LcboAPI api = retrofit.create(LcboAPI.class);
        Response<Stores> response = null;
        response = api.getStores(page, BuildConfig.LCBO_KEY).execute();

        if (response != null && response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }
}
