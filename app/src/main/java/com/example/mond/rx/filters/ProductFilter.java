package com.example.mond.rx.filters;

import com.example.mond.rx.models.products.Result;

public interface ProductFilter {

    int getCount();

    boolean isAppropriate(Result result);
}
