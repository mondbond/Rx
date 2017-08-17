package com.example.mond.rx.data.api;

import com.example.mond.rx.BuildConfig;
import com.example.mond.rx.data.models.products.StoreProducts;
import com.example.mond.rx.data.models.stores.Stores;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LcboAPI {

    String LCBO_TOKEN_KEY = BuildConfig.LCBO_KEY;

    @GET("stores?")
    Observable<Stores> getStores(@Query("page") int page, @Query("access_key") String token);

    @GET("products?")
    Observable<StoreProducts> getProductsByStore(@Query("store_id") int storeId, @Query("page") int page,
                                                 @Query("access_key") String token);
}
