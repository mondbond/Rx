package com.example.mond.rx.screens.main_screen.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.example.mond.rx.app.App;
import com.example.mond.rx.R;
import com.example.mond.rx.common.BaseActivity;
import com.example.mond.rx.di.AppComponent;
import com.example.mond.rx.domain.models.Product;
import com.example.mond.rx.domain.models.Store;
import com.example.mond.rx.screens.main_screen.adapter.ProductsAdapter;
import com.example.mond.rx.screens.main_screen.adapter.StoreAdapter;
import com.example.mond.rx.screens.main_screen.presenter.MainPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {

    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.rc_store)
    RecyclerView mStoreRecycler;

    @BindView(R.id.rc_product)
    RecyclerView mProductRecycler;

    @BindView(R.id.btn_load_stores)
    Button loadBtn;

    StoreAdapter mStoreAdapter;
    ProductsAdapter mProductsAdapter;

    List<Store> mStores = new ArrayList<>();
    List<Product> mProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mStoreAdapter = new StoreAdapter(mStores);
        mProductsAdapter = new ProductsAdapter(mProducts);

        mStoreRecycler.setLayoutManager(new LinearLayoutManager(this));
        mProductRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onDetach(this);
    }

    @OnClick(R.id.btn_load_stores)
    public void getStoreData() {
        if (!mStores.isEmpty()) {
            mPresenter.stopLoadingData();
            mStoreAdapter.clear();
        }

        try {
            mPresenter.setUpStoreData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: +? 7/25/17 The second request will not work without the first one (because the mStores are empty). You can do multiple requests with RxJava one by one.
    //fix this

//    TODO - question: as I understand, I did it already in last commit. in this time I realise separate load of products.

    @OnClick(R.id.btn_load_products)
    public void getProductsData() {
        if (!mProducts.isEmpty()) {
            mPresenter.stopLoadingData();
            mProductsAdapter.clear();
        }

        // TODO: 7/25/17 Please review the sample apps provided and rewrite this using the right RxJava error handling approach
        try {
            mPresenter.setUpProductsByStores(mStores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStore(ArrayList<Store> stores) {
        mStores = stores;
        mStoreAdapter.setNewStores(mStores);
        mStoreRecycler.setAdapter(mStoreAdapter);
    }

    @Override
    public void setProduct(ArrayList<Product> products) {
        mProducts = products;
        if (mProductsAdapter == null) {
            mProductsAdapter = new ProductsAdapter(mProducts);
        } else {
            mProductsAdapter.setNewProduct(mProducts);
        }
        mProductRecycler.setAdapter(mProductsAdapter);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
