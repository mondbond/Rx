package com.example.mond.rx.data.filters;

import com.example.mond.rx.data.models.stores.Result;

public interface StoreFilter {

    int getCount();

    boolean isAppropriate(Result result);
}
