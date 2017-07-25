package com.example.mond.rx.domain;

import com.example.mond.rx.data.filters.ProductFilter;
import com.example.mond.rx.domain.models.Product;

import java.io.IOException;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public interface ProductsRepository {
    Observable<Product> getData(final Retrofit retrofit, int storeId, ProductFilter productFilter) throws IOException;
}
