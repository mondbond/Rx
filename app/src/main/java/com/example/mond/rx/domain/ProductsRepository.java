package com.example.mond.rx.domain;

import com.example.mond.rx.domain.models.Product;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;

public interface ProductsRepository {
    Observable<List<Product>> getProductDataByFilter (int storeId, int page) throws IOException;
}
