package com.example.mond.rx.data.api;

import com.example.mond.rx.data.models.products.StoreProducts;
import com.example.mond.rx.data.models.stores.Stores;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LcboAPI {

    @GET("stores?")
    Observable<Stores> getStores(@Query("page") int page, @Query("access_key") String token);
//
//    @GET("stores?")
//    Call<Stores> getStores(@Query("page") int page, @Query("access_key") String token);

//    @GET("products?")
//    Call<StoreProducts> getProductsByStore(@Query("store_id") String storeId, @Query("page") int page,
//                                           @Query("access_key") String token);

    @GET("products?")
    Observable<StoreProducts> getProductsByStore(@Query("store_id") int storeId, @Query("page") int page,
                                           @Query("access_key") String token);
}
