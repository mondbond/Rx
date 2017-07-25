package com.example.mond.rx.data.mappers;

import com.example.mond.rx.data.models.stores.Result;
import com.example.mond.rx.domain.models.Store;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class StoreMapper implements Function<Result, Store> {
    @Override
    public Store apply(@NonNull Result result) throws Exception {
        return new Store(result.getId(), result.getName());
    }
}
