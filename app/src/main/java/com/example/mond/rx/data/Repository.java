package com.example.mond.rx.data;

import com.example.mond.rx.filters.ProductFilter;
import com.example.mond.rx.filters.StoreFilter;
import com.example.mond.rx.models.simple_models.Product;
import com.example.mond.rx.models.simple_models.Store;

import java.io.IOException;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public interface Repository {
//TODO: is it write way to make repository ?
    // repository interface should be aware of data source (retrofit)
    Observable<Store> getStoresByFilter(final Retrofit retrofit, StoreFilter filter) throws IOException;

    Observable<Product> getProductsByFilter(
            final Retrofit retrofit, int storeId, ProductFilter productFilter) throws IOException;
}
