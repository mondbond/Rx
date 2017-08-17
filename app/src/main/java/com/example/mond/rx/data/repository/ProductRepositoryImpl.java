package com.example.mond.rx.data.repository;

import com.example.mond.rx.data.api.LcboAPI;
import com.example.mond.rx.data.mappers.ProductMapper;
import com.example.mond.rx.domain.ProductsRepository;
import com.example.mond.rx.domain.models.Product;

import java.util.List;

import io.reactivex.Observable;

public class ProductRepositoryImpl implements ProductsRepository {

    private LcboAPI mApi;
    private ProductMapper mProductMapper;

    public ProductRepositoryImpl(LcboAPI lcboAPI) {
        mApi = lcboAPI;
        mProductMapper = new ProductMapper();
    }

    @Override
    public Observable<List<Product>> getProductDataByFilter(int storeId, int page) {
        return mApi.getProductsByStore(storeId, page, LcboAPI.LCBO_TOKEN_KEY)
                .map(products -> products.getResult())
                .flatMap(Observable::fromIterable)
                .map(mProductMapper)
                .toList()
                .toObservable();
    }
}
