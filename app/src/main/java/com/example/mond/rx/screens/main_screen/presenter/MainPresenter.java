package com.example.mond.rx.screens.main_screen.presenter;

import com.example.mond.rx.common.BasePresenter;
import com.example.mond.rx.data.Repository;
import com.example.mond.rx.filters.ProductFilterByFirstLetters;
import com.example.mond.rx.filters.StoreFilterByFirstLetters;
import com.example.mond.rx.models.simple_models.Product;
import com.example.mond.rx.models.simple_models.Store;
import com.example.mond.rx.models.stores.Result;
import com.example.mond.rx.screens.main_screen.view.MainView;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class MainPresenter implements BasePresenter<MainView> {

    private final int STORE_COUNT = 5;
    private final String STORE_SEARCH = "A";

    private final int PRODUCT_COUNT = 5;
    private final String PRODUCT_SEARCH = "A";

    private MainView mView;
    private Retrofit mRetrofit;
    private Repository mRepository;

    @Inject
    public MainPresenter(Retrofit retrofit, Repository repository) {
        mRetrofit = retrofit;
        mRepository = repository;
    }

    @Override
    public void onAttach(MainView view) {
        mView = view;
    }

    @Override
    public void onDetach(MainView view) {
        mView = null;
    }

    public void setUpData() throws IOException {
        Observable<Result> storesObservable = mRepository.getStoresByFilter(mRetrofit,
                new StoreFilterByFirstLetters(STORE_COUNT, STORE_SEARCH));
        Observable<com.example.mond.rx.models.products.Result> prod
                = (Observable<com.example.mond.rx.models.products.Result>) storesObservable
                .flatMap(stores -> {
                    Observable<com.example.mond.rx.models.products.Result> products = null;
                    try {
                        products = mRepository.getProductsByFilter(mRetrofit, stores.getId(),
                                new ProductFilterByFirstLetters(PRODUCT_COUNT, PRODUCT_SEARCH));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return products;
                });

        storesObservable.map(result -> new Store(result.getId(), result.getName()))
                .subscribe(store -> {
                    mView.setStore(store);
                });

        prod.map(product -> new Product(product.getId(), product.getName()))
                .subscribe(product -> {
                    mView.setProduct(product);
                });
    }
}
