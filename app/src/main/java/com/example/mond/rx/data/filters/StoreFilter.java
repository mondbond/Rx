package com.example.mond.rx.data.filters;

import com.example.mond.rx.data.models.stores.Result;
import com.example.mond.rx.domain.models.Store;

public interface StoreFilter {

    int getCount();

    boolean isAppropriate(Store store);
}
