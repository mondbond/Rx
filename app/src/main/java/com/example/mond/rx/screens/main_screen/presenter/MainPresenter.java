package com.example.mond.rx.screens.main_screen.presenter;

import com.example.mond.rx.common.BasePresenter;
import com.example.mond.rx.data.filters.StoreFilter;
import com.example.mond.rx.domain.ProductsRepository;
import com.example.mond.rx.domain.StoreRepository;
import com.example.mond.rx.data.filters.ProductFilterByFirstLetters;
import com.example.mond.rx.data.filters.StoreFilterByFirstLetters;
import com.example.mond.rx.domain.models.Product;
import com.example.mond.rx.domain.models.Store;
import com.example.mond.rx.screens.main_screen.view.MainView;

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

public class MainPresenter implements BasePresenter<MainView> {

    private final int LCBO_START_PAGE = 1;

    private final int STORE_COUNT = 5;
    private final String STORE_SEARCH = "B";

    private final int PRODUCT_COUNT = 20;
    private final String PRODUCT_SEARCH = "B";

    private final boolean IS_LAST_PAGE = true;

    private MainView mView;
    private ProductsRepository mProductsRepository;
    private StoreRepository mStoreRepository;

    int mStorePage = 1;
    int mStoreAccepted = 0;
    boolean mIsStoreLastPage = false;
    ArrayList<Store> mFilteredStores = new ArrayList<>();

    int mProductPage = 1;
    int mProductsAccepted = 0;
    boolean mIsProductLastPage = false;

    HashMap<Integer, Integer> mFilteredStoreProductsCount = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> mStoreProductsPage = new HashMap<Integer, Integer>();
    HashMap<Integer, Boolean> mStoreProductsIsLastPage = new HashMap<Integer, Boolean>();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    public MainPresenter(StoreRepository storeRepository,
                         ProductsRepository productsRepository) {
        mStoreRepository = storeRepository;
        mProductsRepository = productsRepository;
    }

    @Override
    public void onAttach(MainView view) {
        mView = view;
    }

    @Override
    public void onDetach(MainView view) {
        stopLoadingData();
        mView = null;
    }

    public void setUpStores() {
        stopLoadingData();
        initStoreParams();
        StoreFilterByFirstLetters filter = new StoreFilterByFirstLetters(STORE_SEARCH);
        getStoresByFilterAndShow(filter);
    }

    private void initStoreParams() {
        mFilteredStores.clear();
        mStoreAccepted = 0;
        mIsStoreLastPage = false;
        mStorePage = 1;
    }

    private void getStoresByFilterAndShow(StoreFilter filter) {
        mCompositeDisposable.add(mStoreRepository.getDataByFilter(mStorePage)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Store>, Observable<Store>>() {
                    @Override
                    public Observable<Store> apply(@NonNull List<Store> stores) throws Exception {
                        if (stores.isEmpty()) {
                            mIsStoreLastPage = true;
                        }
                        return Observable.fromIterable(stores);
                    }
                })
                .filter(filter::isAppropriate)
                .subscribe(
                        store -> {
                            if (isNeedMoreStores()) {
                                mView.setStore(store);
                                mStoreAccepted++;
                            }
                        },
                        this::showError,
                        () -> {
                            if (isNeedMoreStores()) {
                                mStorePage++;
                                if (!mIsStoreLastPage) {
                                    getStoresByFilterAndShow(filter);
                                }
                            }
                        }
                ));
    }

    public void setUpProductsByStores(List<Store> stores) {

        initProductsParams(stores);

        Observable.fromIterable(stores).forEach(store -> {
            loadStoreProductsByFilterAndShow(store.getId(), LCBO_START_PAGE);
        });
    }

    private void initProductsParams(List<Store> stores) {
        mProductsAccepted = 0;
        mIsProductLastPage = !IS_LAST_PAGE;
        mProductPage = 1;

        mFilteredStoreProductsCount.clear();
        mStoreProductsPage.clear();
        mStoreProductsIsLastPage.clear();

        for (Store item : stores) {
            mFilteredStoreProductsCount.put(item.getId(), 0);
            mStoreProductsPage.put(item.getId(), 1);
            mStoreProductsIsLastPage.put(item.getId(), !IS_LAST_PAGE);
        }
    }

    public void loadStoreProductsByFilterAndShow(int storeId, int page) {
        ProductFilterByFirstLetters filter = new ProductFilterByFirstLetters(PRODUCT_SEARCH);
        mCompositeDisposable.add(mProductsRepository.getProductDataByFilter(storeId, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Product>, Observable<Product>>() {
                    @Override
                    public Observable<Product> apply(@NonNull List<Product> products) throws Exception {
                        if (products.isEmpty()) {
                            mStoreProductsIsLastPage.put(storeId, IS_LAST_PAGE);
                        }
                        return Observable.fromIterable(products);
                    }
                })
                .filter(filter::isAppropriate)
                .subscribe(
                        product -> {
                            if (isNeedMoreProducts(storeId)) {
                                mView.setProduct(product);
                                mFilteredStoreProductsCount.put(storeId, mFilteredStoreProductsCount.get(storeId) + 1);
                            }
                        },
                        this::showError,
                        () -> {
                            if (isNeedMoreProducts(storeId)) {
                                mStoreProductsPage.put(storeId, mStoreProductsPage.get(storeId) + 1);
                                if (!mStoreProductsIsLastPage.get(storeId)) {
                                    loadStoreProductsByFilterAndShow(storeId, mStoreProductsPage.get(storeId));
                                }
                            }
                        }
                ));
    }

    private void showError(Throwable t) {
        mView.showError(t.toString());
    }

    public void stopLoadingData() {
        mCompositeDisposable.clear();
    }

    private boolean isNeedMoreStores() {
        return mStoreAccepted < STORE_COUNT;
    }

    private boolean isNeedMoreProducts(int storeId) {
        return mFilteredStoreProductsCount.get(storeId) < PRODUCT_COUNT;
    }
}
