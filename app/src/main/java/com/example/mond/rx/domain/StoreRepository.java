package com.example.mond.rx.domain;

import com.example.mond.rx.data.filters.StoreFilter;
import com.example.mond.rx.domain.models.Store;

import java.io.IOException;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public interface StoreRepository {
    Observable<Store> getDataByFilter (StoreFilter filter) throws IOException;
}
