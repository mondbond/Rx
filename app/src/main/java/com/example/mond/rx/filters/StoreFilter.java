package com.example.mond.rx.filters;

import com.example.mond.rx.models.stores.Result;

public interface StoreFilter {

    int getCount();

    boolean isAppropriate(Result result);
}
