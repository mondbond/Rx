package com.example.mond.rx.screens.main_screen.presenter;

import android.util.Log;

import com.example.mond.rx.common.BasePresenter;
import com.example.mond.rx.data.filters.StoreFilter;
import com.example.mond.rx.domain.ProductsRepository;
import com.example.mond.rx.domain.StoreRepository;
import com.example.mond.rx.data.filters.ProductFilterByFirstLetters;
import com.example.mond.rx.data.filters.StoreFilterByFirstLetters;
import com.example.mond.rx.domain.models.Product;
import com.example.mond.rx.domain.models.Store;
import com.example.mond.rx.screens.main_screen.view.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainPresenter implements BasePresenter<MainView> {

    private final int START_PAGE = 1;

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

    int page = 1;
    int accepted = 0;
    final int count = 40;
    boolean mIsStoreLastPage = false;
    ArrayList<Store> mSortedStores = new ArrayList<>();

    public void setUpStoreData() throws IOException {
        initStoreParams();
        StoreFilterByFirstLetters filter = new StoreFilterByFirstLetters(5, "Ba");
        getStoreDataByFilter(mSortedStores, filter);
    }

    private ArrayList<Store> getStoreDataByFilter(ArrayList<Store> sortedStores, StoreFilter filter) throws IOException {

        mStoreRepository.getDataByFilter(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Store>, Observable<Store>>() {
                    @Override
                    public Observable<Store> apply(@NonNull List<Store> stores) throws Exception {
                        if(stores.isEmpty()){
                            mIsStoreLastPage = true;
                        }
                        return Observable.fromIterable(stores);
                    }
                })
                .filter(store -> {return filter.isAppropriate(store);})
                .subscribe(
                        store -> {
                            if(accepted < count) {
                                sortedStores.add(store);
                                accepted++;
                            }
                        },
                        throwable -> {
                            if(mView != null) {
                                mView.showError(throwable.toString());
                            }
                        },
                        () -> {
                            if(mView != null) {
                                if(accepted < count) {
                                    page++;
                                    mView.setStore(sortedStores);
                                    if(!mIsStoreLastPage) {
                                        getStoreDataByFilter(sortedStores, filter);
                                    }
                                }else {
                                    mView.setStore(sortedStores);
                                }
                            }
                        }
                );

        return sortedStores;
    }

    private void initStoreParams() {
        accepted = 0;
        mIsStoreLastPage = false;
        page = 1;
    }

    int mProductPage = 1;
    int mProductsAccepted = 0;
    final int mProductsCount = 40;
    boolean mIsProductLastPage = false;
    ArrayList<Product> mSortedProducts = new ArrayList<>();

    HashMap<Integer, Integer> mStoreProducts = new HashMap<Integer, Integer>();

    private void initProductsParams() {
        mProductsAccepted = 0;
        mIsProductLastPage = false;
        mProductPage = 1;
    }


    public void setUpProductsByStores(List<Store> stores) throws IOException {

        ProductFilterByFirstLetters filter = new ProductFilterByFirstLetters(20, "A");

        Observable.fromIterable(mSortedStores)
//                .flatMap(store -> mProductsRepository.getProductDataByFilter(store.getId(), mProductPage))
        .flatMap(new Function<Store, Observable<Product>>() {
            @Override
            public Observable<Product> apply(@NonNull Store stores) throws Exception {
                int page = 0;
                boolean isLastProductPage = false;
                mProductsRepository.getProductDataByFilter(stores.getId(), page)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(products -> {return Observable.fromIterable(products)})
                        .filter(product -> {return filter.isAppropriate(product);})
                        .subscribe(
                                product -> {
                                    if(mStoreProducts.get(stores.getId()) < count) {
                                        mSortedProducts.add(product);
                                        mStoreProducts.put(stores.getId(), mSortedProducts.get(stores.getId()) + 1);
                                    }
                                },
                                throwable -> {
                                    if(mView != null) {
                                        mView.showError(throwable.toString());
                                    }
                                },
                                () -> {
                                    if(mView != null) {
                                        if(accepted < count) {
                                            page++;
                                            Log.d("MORE", "==========" + "  " + sortedStores.size());
                                            mView.setStore(sortedStores);
                                            if(!mIsStoreLastPage) {
                                                getStoreDataByFilter(sortedStores, filter);
                                            }
                                        }else {
                                            mView.setStore(sortedStores);
                                        }
                                    }
                                }
                        );
            }
        });

        // TODO: 25.07.17 is this normal ?
        // TODO: 7/25/17 No.
        // TODO: 7/25/17 Check this everywhere
        // 1. You don't need to pass retrofit instance. You will just need a interface that was created by retrofit.create(Interface_Name.class);
        // 2. You are creating multiple observables in the cycle.
        // 3. View instance may be null (onDetach worked before you got any response)

//        TODO - question. I check view before use in this method. Or It's not enough ?

        // Read about rxJava operators and please look through the sample apps that were given in android chat ("https://github.com/EugeneYovbak/ReactiveApp", "https://Zolotar_Oleg@bitbucket.org/Zolotar_Oleg/hitbtc.git")
        // Read about filtering in RxJava and use it
    }

    public void stopLoadingData() {
        mCompositeDisposable.clear();
    }
}
