package com.example.mond.rx.data.repository;

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
    @Override
    public Observable<Store> getData(final Retrofit retrofit, StoreFilter filter) throws IOException {
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
                    stores = getStoresByRetrofit(retrofit, page);
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

    private Stores getStoresByRetrofit(Retrofit retrofit, int page) {

        LcboAPI api = retrofit.create(LcboAPI.class);
        Response<Stores> response = null;

        try {
            response = api.getStores(page, BuildConfig.LCBO_KEY).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }
}
