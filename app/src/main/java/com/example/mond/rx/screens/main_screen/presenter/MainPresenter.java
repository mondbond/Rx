package com.example.mond.rx.screens.main_screen.presenter;

import com.example.mond.rx.common.BasePresenter;
import com.example.mond.rx.data.Repository;
import com.example.mond.rx.filters.ProductFilterByFirstLetters;
import com.example.mond.rx.filters.StoreFilterByFirstLetters;
import com.example.mond.rx.models.simple_models.Product;
import com.example.mond.rx.models.simple_models.Store;
import com.example.mond.rx.screens.main_screen.view.MainView;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;

public class MainPresenter implements BasePresenter<MainView> {

    private final int STORE_COUNT = 5;
    private final String STORE_SEARCH = "A";

    private final int PRODUCT_COUNT = 5;
    private final String PRODUCT_SEARCH = "A";

    private MainView mView;
    private Retrofit mRetrofit;
    private Repository mRepository;

    // TODO: 20/07/17 use composite disposable
    private Disposable mDisposableStore;
    private Disposable mDisposableProduct;

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

    // TODO: 20/07/17 add possibility to load product for store when you click on it
    public void setUpData() throws IOException {
        Observable<Store> storesObservable = mRepository.getStoresByFilter(mRetrofit,
                new StoreFilterByFirstLetters(STORE_COUNT, STORE_SEARCH));
        Observable<Product> prod
                = (Observable<Product>) storesObservable
//                TODO: is this a good exp to use flatMap operator in such case ?
                .flatMap(stores -> {
                    // TODO: 20/07/17 why you need the catch here? throw exception and handle when subscribe on it
                    Observable<Product> products = null;
                    try {
                        products = mRepository.getProductsByFilter(mRetrofit, stores.getId(),
                                new ProductFilterByFirstLetters(PRODUCT_COUNT, PRODUCT_SEARCH));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return products;
                });

        mDisposableStore = storesObservable.subscribe(store -> {
                    mView.setStore(store);
                });

        mDisposableProduct = prod.subscribe(product -> {
                    mView.setProduct(product);
                });
    }

    public void stopLoadingData() {
        mDisposableStore.dispose();
        mDisposableProduct.dispose();
    }
}
