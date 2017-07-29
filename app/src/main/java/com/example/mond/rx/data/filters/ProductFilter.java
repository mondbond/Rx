package com.example.mond.rx.data.filters;

import com.example.mond.rx.data.models.products.Result;
import com.example.mond.rx.domain.models.Product;

public interface ProductFilter {

    int getCount();

    boolean isAppropriate(Product poduct);
}
