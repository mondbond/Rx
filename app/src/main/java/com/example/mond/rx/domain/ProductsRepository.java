package com.example.mond.rx.domain;

import com.example.mond.rx.data.filters.ProductFilter;
import com.example.mond.rx.domain.models.Product;

import java.io.IOException;

import io.reactivex.Observable;

public interface ProductsRepository {
    Observable<Product> getDataByFilter (int storeId, ProductFilter productFilter) throws IOException;
}
