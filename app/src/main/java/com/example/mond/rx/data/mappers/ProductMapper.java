package com.example.mond.rx.data.mappers;

import com.example.mond.rx.data.models.products.Result;
import com.example.mond.rx.domain.models.Product;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class ProductMapper implements Function<Result, Product> {
    @Override
    public Product apply(@NonNull Result result) throws Exception {
        return new Product(result.getId(), result.getName());
    }
}
