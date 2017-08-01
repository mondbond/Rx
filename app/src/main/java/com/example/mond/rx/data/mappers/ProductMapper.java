package com.example.mond.rx.data.mappers;

import com.example.mond.rx.data.models.products.Result;
import com.example.mond.rx.domain.models.Product;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

// TODO: 01/08/17 mapper should be item to item
public class ProductMapper implements Function<List<Result>, List<Product>> {
    @Override
    public List<Product> apply(@NonNull List<Result> results) throws Exception {
        // TODO: 01/08/17 you don't know what implementation of list should be used
        ArrayList<Product> products = new ArrayList<>();
        for (Result result : results) {
            products.add(new Product(result.getId(), result.getName()));
        }
        return products;
    }
}
