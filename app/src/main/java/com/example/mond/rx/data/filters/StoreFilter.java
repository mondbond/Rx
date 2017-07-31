package com.example.mond.rx.data.filters;

import com.example.mond.rx.domain.models.Store;

public interface StoreFilter {

    boolean isAppropriate(Store store);
}
