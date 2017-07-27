package com.example.mond.rx.data.repository;

import com.example.mond.rx.BuildConfig;
import com.example.mond.rx.data.mappers.ProductMapper;
import com.example.mond.rx.data.mappers.StoreMapper;
import com.example.mond.rx.domain.ProductsRepository;
import com.example.mond.rx.data.filters.ProductFilter;
import com.example.mond.rx.data.models.products.StoreProducts;
import com.example.mond.rx.domain.models.Product;
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

public class ProductRepositoryImpl implements ProductsRepository {
    // TODO: 7/25/17 The same as for store repository. This usage of RxJava is bad.
    //Read about rxJava operators and think through the logic of receiving data. This could be written in few lines of code without the try catch.

    @Override
    public Observable<Product> getData(final Retrofit retrofit, int storeId, ProductFilter filter)
            throws IOException {

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
                List<com.example.mond.rx.data.models.products.Result> results;

                while (count < filter.getCount() || isLastPage) {
                    storeProducts = getProductsByRetrofit(retrofit, storeId, page);
                    if (storeProducts != null) {

                        isLastPage = storeProducts.getPager().isIsFinalPage();
                        results = storeProducts.getResult();

                        Iterator<com.example.mond.rx.data.models.products.Result> iterator = results.iterator();
                        while (iterator.hasNext()) {
                            if (!filter.isAppropriate(iterator.next()) || count > filter.getCount()) {
                                iterator.remove();
                            } else {

                                ++count;
                            }
                        }
                        Observable.fromIterable(results)
                                .map(new ProductMapper())
                                .subscribe(e::onNext);
                        page++;
                    }
                }
            }
        });
    }

    private StoreProducts getProductsByRetrofit(Retrofit retrofit, int storeId, int page) {
        LcboAPI api = retrofit.create(LcboAPI.class);
        Response<StoreProducts> response = null;

        try {
            response = api.getProductsByStore(String.valueOf(storeId), page,
                    BuildConfig.LCBO_KEY).execute();
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
