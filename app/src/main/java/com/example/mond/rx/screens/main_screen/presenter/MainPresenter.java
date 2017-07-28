package com.example.mond.rx.screens.main_screen.presenter;

import com.example.mond.rx.common.BasePresenter;
import com.example.mond.rx.domain.ProductsRepository;
import com.example.mond.rx.domain.StoreRepository;
import com.example.mond.rx.data.filters.ProductFilterByFirstLetters;
import com.example.mond.rx.data.filters.StoreFilterByFirstLetters;
import com.example.mond.rx.domain.models.Product;
import com.example.mond.rx.domain.models.Store;
import com.example.mond.rx.screens.main_screen.view.MainView;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainPresenter implements BasePresenter<MainView> {

    private final int STORE_COUNT = 5;
    private final String STORE_SEARCH = "B";

    private final int PRODUCT_COUNT = 20;
    private final String PRODUCT_SEARCH = "A";

    private MainView mView;
    private Retrofit mRetrofit;
    private ProductsRepository mProductsRepository;
    private StoreRepository mStoreRepository;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    public MainPresenter(Retrofit retrofit, StoreRepository storeRepository,
                         ProductsRepository productsRepository) {
        mRetrofit = retrofit;
        mStoreRepository = storeRepository;
        mProductsRepository = productsRepository;
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
        Observable<Store> storesObservable = mStoreRepository.getDataByFilter(new StoreFilterByFirstLetters(STORE_COUNT, STORE_SEARCH))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        mCompositeDisposable.add(storesObservable.subscribe(
            store -> {
                if(mView != null) {
                    mView.setStore(store);
                }
            },
            throwable -> {
                if(mView != null) {
                    mView.showError(throwable.toString());
                }
            }
        ));
    }

    public void setUpProductsByStores(List<Store> stores) throws IOException {

        // TODO: 25.07.17 is this normal ?

        // TODO: 7/25/17 No.
        // TODO: 7/25/17 Check this everywhere
        // 1. You don't need to pass retrofit instance. You will just need a interface that was created by retrofit.create(Interface_Name.class);
        // 2. You are creating multiple observables in the cycle.
        // 3. View instance may be null (onDetach worked before you got any response)

//        TODO - question. I check view before use in this method. Or It's not enough ?

        // Read about rxJava operators and please look through the sample apps that were given in android chat ("https://github.com/EugeneYovbak/ReactiveApp", "https://Zolotar_Oleg@bitbucket.org/Zolotar_Oleg/hitbtc.git")
        // Read about filtering in RxJava and use it
        for (Store item : stores) {
            Observable<Product> prod = mProductsRepository.getDataByFilter(item.getId(),
                    new ProductFilterByFirstLetters(PRODUCT_COUNT, PRODUCT_SEARCH))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());

            mCompositeDisposable.add(prod.subscribe(
                product -> {
                    if(mView != null) {
                        mView.setProduct(product);
                    }
                },
                throwable -> {
                    if(mView != null) {
                        mView.showError(throwable.toString());
                    }
                }
            ));
        }
    }

    public void stopLoadingData() {
        mCompositeDisposable.clear();
    }
}
