package com.example.mond.rx.filters;

import com.example.mond.rx.models.stores.Result;

/**
 * Created by mond on 18.07.17.
 */

public interface StoreFilter {

    int getCount();

    boolean isAppropriate(Result result);
}
