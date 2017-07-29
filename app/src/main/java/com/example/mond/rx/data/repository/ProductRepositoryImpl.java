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
    // TODO: 7/25/17 The same as for store repository. This usage of RxJava is bad.
    //Read about rxJava operators and think through the logic of receiving data. This could be written in few lines of code without the try catch.

    private Retrofit mRetrofit;

    public ProductRepositoryImpl(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public Observable<List<Product>> getProductDataByFilter(int storeId, int page) throws IOException {
        LcboAPI api = mRetrofit.create(LcboAPI.class);
        return api.getProductsByStore(storeId, page, BuildConfig.LCBO_KEY)
                .map(products ->products.getResult())
                .map(new ProductMapper());
    }
}
