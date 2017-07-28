package com.example.mond.rx.data.mappers;

import com.example.mond.rx.data.models.stores.Result;
import com.example.mond.rx.domain.models.Store;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class StoreMapper implements Function<List<Result>, List<Store>> {
    @Override
    public List<Store> apply(@NonNull List<Result> results) throws Exception {
        ArrayList<Store> stores = new ArrayList<>();
        for(Result result : results) {
            stores.add(new Store(result.getId(), result.getName()));
        }
        return stores;
    }
}
