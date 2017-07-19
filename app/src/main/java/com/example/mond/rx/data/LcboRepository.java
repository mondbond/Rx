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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LcboRepository {

    private String key = "MDoyNjllYjU3NC02ODdlLTExZTctOGZhNS1iZmRlOWJiOTFkNjg6WkF0UWVBU2MzRFhKaml6YmRxWmYyZm1qWXdBeXRSeTZMcDFF";

    public Observable<Result> getStoresByFilter(final Retrofit retrofit, StoreFilter storeFilter) throws IOException {
        return Observable.create(new ObservableOnSubscribe<Result>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Result> e) throws Exception {
                LcboAPI api = retrofit.create(LcboAPI.class);
                api.getStores(key).enqueue(new Callback<Stores>() {
                    @Override
                    public void onResponse(Call<Stores> call, Response<Stores> response) {
                        if(response.isSuccessful()) {

                            List<Result> results = response.body().getResult()
                                    .subList(0, 1);
                            for(Result item : results) {
                                e.onNext(item);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Stores> call, Throwable t) {

                    }
                });
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<com.example.mond.rx.models.products.Result> getProductsByFilter(final Retrofit retrofit, int storeId, ProductFilter filter) throws IOException {
        return  Observable.create(new ObservableOnSubscribe<com.example.mond.rx.models.products.Result>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<com.example.mond.rx.models.products.Result> e) throws Exception {
                int page = 1;
                int count = 0;
                boolean isLastPage = false;

                List<com.example.mond.rx.models.products.Result> results = new ArrayList<com.example.mond.rx.models.products.Result>();
                while (count < filter.getCount() || isLastPage) {

//                    up
                    StoreProducts storeProducts = getProductsByRetrofit(retrofit, storeId, page);
                    isLastPage = storeProducts.getPager().isIsFinalPage();
                    results = storeProducts.getResult();

                    for(com.example.mond.rx.models.products.Result item : results) {

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

    private StoreProducts getProductsByRetrofit (Retrofit retrofit, int storeId, int page) {

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
