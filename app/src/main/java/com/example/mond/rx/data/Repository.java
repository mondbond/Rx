package com.example.mond.rx.data;

import com.example.mond.rx.filters.ProductFilter;
import com.example.mond.rx.filters.StoreFilter;
import com.example.mond.rx.models.stores.Result;

import java.io.IOException;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public interface Repository {

    Observable<Result> getStoresByFilter(final Retrofit retrofit, StoreFilter filter) throws IOException;

    Observable<com.example.mond.rx.models.products.Result> getProductsByFilter(
            final Retrofit retrofit, int storeId, ProductFilter productFilter) throws IOException;
}
