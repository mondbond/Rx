package com.example.mond.rx.data.repository;

import com.example.mond.rx.BuildConfig;
import com.example.mond.rx.data.mappers.ProductMapper;
import com.example.mond.rx.domain.ProductsRepository;
import com.example.mond.rx.domain.models.Product;
import com.example.mond.rx.data.api.LcboAPI;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class ProductRepositoryImpl implements ProductsRepository {

    private Retrofit mRetrofit;

    public ProductRepositoryImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public Observable<List<Product>> getProductDataByFilter(int storeId, int page) {
        LcboAPI api = mRetrofit.create(LcboAPI.class);
        return api.getProductsByStore(storeId, page, BuildConfig.LCBO_KEY)
                .map(products -> products.getResult())
                .map(new ProductMapper());
    }
}
