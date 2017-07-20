package com.example.mond.rx.data;

import com.example.mond.rx.filters.StoreFilter;
import com.example.mond.rx.filters.ProductFilter;
import com.example.mond.rx.models.products.StoreProducts;
import com.example.mond.rx.models.stores.Result;
import com.example.mond.rx.models.stores.Stores;
import com.example.mond.rx.retrofit.LcboAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LcboRepository implements Repository {

    private String key = "MDoyNjllYjU3NC02ODdlLTExZTctOGZhNS1iZmRlOWJiOTFkNjg6WkF0UWVBU2MzRFhKaml6YmRxWmYyZm1qWXdBeXRSeTZMcDFF";

    public Observable<Result> getStoresByFilter(final Retrofit retrofit, StoreFilter filter) throws IOException {
        return Observable.create(new ObservableOnSubscribe<Result>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Result> e) throws Exception {

                int page = 1;
                int count = 0;
                boolean isLastPage = false;

                Stores stores = null;
                List<Result> results = new ArrayList<>();

                while (count < filter.getCount() || isLastPage) {
                    stores = getStoresByRetrofit(retrofit, page);
                    isLastPage = stores.getStoresPager().isIsFinalPage();
                    results = stores.getResult();
                    for(Result item : results) {
                        if(filter != null && filter.isAppropriate(item) && count < filter.getCount()) {
                            ++count;
                            e.onNext(item);
                        }
                    }
                    page++;
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    private Stores getStoresByRetrofit(Retrofit retrofit, int page) {

        LcboAPI api = retrofit.create(LcboAPI.class);
        Response<Stores> products = null;

        try {
            products = api.getStores(page, key).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(products != null && products.isSuccessful()) {
            return products.body();
        }else {
            return null;
        }
    }

    public Observable<com.example.mond.rx.models.products.Result> getProductsByFilter(
            final Retrofit retrofit, int storeId, ProductFilter productFilter) throws IOException {
        return  Observable.create(new ObservableOnSubscribe<com.example.mond.rx.models.products.Result>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<com.example.mond.rx.models.products.Result> e) throws Exception {
                int page = 1;
                int count = 0;
                boolean isLastPage = false;

                StoreProducts storeProducts = null;

                List<com.example.mond.rx.models.products.Result> results = new ArrayList<com.example.mond.rx.models.products.Result>();
                while (count < productFilter.getCount() || isLastPage) {
                    storeProducts = getProductsByRetrofit(retrofit, storeId, page);
                    isLastPage = storeProducts.getPager().isIsFinalPage();
                    results = storeProducts.getResult();

                    for(com.example.mond.rx.models.products.Result item : results) {
                        if(productFilter != null && productFilter.isAppropriate(item) && count < productFilter.getCount()) {
                            ++count;
                            e.onNext(item);
                        }
                    }
                    page++;
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    private StoreProducts getProductsByRetrofit(Retrofit retrofit, int storeId, int page) {

        LcboAPI api = retrofit.create(LcboAPI.class);
        Response<StoreProducts> products = null;

        try {
            products = api.getProductsByStore(String.valueOf(storeId), page, key).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
         if(products != null && products.isSuccessful()) {
             return products.body();
         }else {
             return null;
         }
    }
}
