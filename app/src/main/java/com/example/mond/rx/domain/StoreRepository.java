package com.example.mond.rx.domain;

import com.example.mond.rx.domain.models.Store;
import java.io.IOException;
import java.util.List;
import io.reactivex.Observable;

public interface StoreRepository {
    Observable<List<Store>> getDataByFilter (int page) throws IOException;
}
