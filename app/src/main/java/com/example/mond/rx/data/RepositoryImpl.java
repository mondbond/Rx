package com.example.mond.rx.data;

import android.content.Context;

import com.example.mond.rx.R;
import com.example.mond.rx.filters.StoreFilter;
import com.example.mond.rx.filters.ProductFilter;
import com.example.mond.rx.models.products.StoreProducts;
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

public class RepositoryImpl implements Repository {

    private Context mContext;

    public RepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<Result> getStoresByFilter(final Retrofit retrofit, StoreFilter filter) throws IOException {
            if (filter == null) {
                throw new NullPointerException("Filter is null");
            }
        return Observable.create(new ObservableOnSubscribe<Result>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Result> e) throws Exception {
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
                        for (Result item : results) {
                            if (filter.isAppropriate(item) && count < filter.getCount()) {
                                ++count;
                                e.onNext(item);
                            }
                        }
                        page++;
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    private Stores getStoresByRetrofit(Retrofit retrofit, int page) {
        LcboAPI api = retrofit.create(LcboAPI.class);
        Response<Stores> response = null;

        try {
            response = api.getStores(page, mContext.getResources().getString(R.string.lcbo_key)).execute();
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
    public Observable<com.example.mond.rx.models.products.Result> getProductsByFilter(
            final Retrofit retrofit, int storeId, ProductFilter filter) throws IOException {
            if (filter == null) {
                throw new NullPointerException("Filter is null");
            }
        return Observable.create(new ObservableOnSubscribe<com.example.mond.rx.models.products.Result>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<com.example.mond.rx.models.products.Result> e) throws Exception {
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
                                e.onNext(item);
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
                    mContext.getResources().getString(R.string.lcbo_key)).execute();
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
