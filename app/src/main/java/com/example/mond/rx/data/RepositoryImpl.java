package com.example.mond.rx.data;

import com.example.mond.rx.Config;
import com.example.mond.rx.filters.StoreFilter;
import com.example.mond.rx.filters.ProductFilter;
import com.example.mond.rx.models.products.StoreProducts;
import com.example.mond.rx.models.simple_models.Product;
import com.example.mond.rx.models.simple_models.Store;
import com.example.mond.rx.models.stores.Result;
import com.example.mond.rx.models.stores.Stores;
import com.example.mond.rx.retrofit.LcboAPI;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

//todo clean up the mess
// TODO: 20/07/17 separate stores repository & products repository
public class RepositoryImpl implements Repository {

    @Override
    public Observable<Store> getStoresByFilter(final Retrofit retrofit, StoreFilter filter) throws IOException {
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
                // TODO: 20/07/17 use rx functions, mappers, work in react way
                while (count < filter.getCount() || isLastPage) {
                    stores = getStoresByRetrofit(retrofit, page);
                    if (stores != null) {
                        isLastPage = stores.getStoresPager().isIsFinalPage();
                        results = stores.getResult();
                        for (Result item : results) {
                            if (filter.isAppropriate(item) && count < filter.getCount()) {
                                ++count;
                                e.onNext(new Store(item.getId(), item.getName()));
                            }
                        }
                        page++;
                    }
                }
            }
        })
                // todo repository don't know about threads, just get data from retrofit, convert it to appropriate view and throw to others
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    private Stores getStoresByRetrofit(Retrofit retrofit, int page) {
        LcboAPI api = retrofit.create(LcboAPI.class);
        Response<Stores> response = null;

        try {
            response = api.getStores(page, Config.LCBO_KEY).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()) {
            return response.body();
        } else {
            return null;
        }
    }

    @Override
    public Observable<Product> getProductsByFilter(
            final Retrofit retrofit, int storeId, ProductFilter filter) throws IOException {
            if (filter == null) {
                throw new NullPointerException("Filter is null");
            }
        return Observable.create(new ObservableOnSubscribe<Product>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Product> e) throws Exception {
                int page = 1;
                int count = 0;
                boolean isLastPage = false;

                StoreProducts storeProducts;
                List<com.example.mond.rx.models.products.Result> results;

                while (count < filter.getCount() || isLastPage) {
                    storeProducts = getProductsByRetrofit(retrofit, storeId, page);
                    if (storeProducts != null) {
                        isLastPage = storeProducts.getPager().isIsFinalPage();
                        results = storeProducts.getResult();

                        for (com.example.mond.rx.models.products.Result item : results) {
                            if (filter.isAppropriate(item) && count < filter.getCount()) {
                                ++count;
                                e.onNext(new Product(item.getId(), item.getName()));
                            }
                        }
                        page++;
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    private StoreProducts getProductsByRetrofit(Retrofit retrofit, int storeId, int page) {
        LcboAPI api = retrofit.create(LcboAPI.class);
        Response<StoreProducts> response = null;

        try {
            response = api.getProductsByStore(String.valueOf(storeId), page,
                    Config.LCBO_KEY).execute();
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
