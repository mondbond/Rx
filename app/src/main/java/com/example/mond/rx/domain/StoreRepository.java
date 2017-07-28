package com.example.mond.rx.domain;

import com.example.mond.rx.data.filters.StoreFilter;
import com.example.mond.rx.domain.models.Store;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public interface StoreRepository {
    Observable<Store> getDataByFilter (int page) throws IOException;
}
