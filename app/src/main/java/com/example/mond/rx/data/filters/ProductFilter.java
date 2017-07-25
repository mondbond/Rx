package com.example.mond.rx.data.filters;

import com.example.mond.rx.data.models.products.Result;

public interface ProductFilter {

    int getCount();

    boolean isAppropriate(Result result);
}
