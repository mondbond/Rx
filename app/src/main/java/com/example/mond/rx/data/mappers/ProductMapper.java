package com.example.mond.rx.data.mappers;

import com.example.mond.rx.data.models.products.Result;
import com.example.mond.rx.domain.models.Product;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class ProductMapper implements Function<List<Result>, List<Product>> {
    @Override
    public List<Product> apply(@NonNull List<Result> results) throws Exception {
        ArrayList<Product> products = new ArrayList<>();
        for(Result result : results) {
            products.add(new Product(result.getId(), result.getName()));
        }
        return products;
    }
}
