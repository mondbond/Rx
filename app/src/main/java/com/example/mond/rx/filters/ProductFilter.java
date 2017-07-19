package com.example.mond.rx.filters;

import com.example.mond.rx.models.products.Result;

/**
 * Created by mond on 19.07.17.
 */

public interface ProductFilter {

    int getCount();
    boolean isAppropriate(Result result);
}
