package com.example.mond.rx.screens.main_screen.presenter;

import android.util.Log;

import com.example.mond.rx.data.LcboRepository;
import com.example.mond.rx.common.BasePresenter;
import com.example.mond.rx.filters.FilterByFirstLetters;
import com.example.mond.rx.models.stores.Result;
import com.example.mond.rx.screens.main_screen.view.MainView;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class MainPresenter implements BasePresenter<MainView> {

    private MainView mView;
    private Retrofit mRetrofit;

    @Inject
    public MainPresenter(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public void onAttach(MainView view) {
        Log.d("ATTACH", "=");
        mView = view;
    }

    @Override
    public void onDetach(MainView view) {
        Log.d("DETACH", "=");
        mView = null;
    }

    public void getData() throws IOException {

        LcboRepository repository = new LcboRepository();

            Observable<Result> storesObservable = repository.getStoresByFilter(mRetrofit, null);

            Observable<com.example.mond.rx.models.products.Result> prod = (Observable<com.example.mond.rx.models.products.Result>) storesObservable.flatMap(stores -> {
                Observable<com.example.mond.rx.models.products.Result> products = null;
                try {
                     products = repository.getProductsByFilter(mRetrofit, stores.getId(), new FilterByFirstLetters(20, "A"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return products;
            });

        storesObservable.subscribe(result -> {
            mView.setStores(result);
           Log.d("STORE", result.toString());
        });

        prod.subscribe(products -> {
            Log.d("PRODUCTS", "= " + products.toString());
            ArrayList<com.example.mond.rx.models.products.Result> arrayList = new ArrayList<com.example.mond.rx.models.products.Result>();
            arrayList.add(products);
            mView.setProducts(arrayList);
        });

//
//            storesObservable.subscribe(stores -> {
//
//                List<Result> result = stores.getResult();
//                List<Result> res = result.subList(0, 5);
//                mView.setStores(res);
//
//                for(Result item : res){
//                    getProductsByFilter(item.getId());
//                }
//            });
    }

//    public void getProductsByFilter(int storeId) {
//        LcboRepository repository = new LcboRepository();
//        try {
//            repository.getProductsByFilter(mRetrofit, storeId, 1).subscribe(products -> {
//                List<com.example.mond.rx.models.products.Result> result = products.getResult().subList(0, 20);
//                mView.setProducts(result);
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
