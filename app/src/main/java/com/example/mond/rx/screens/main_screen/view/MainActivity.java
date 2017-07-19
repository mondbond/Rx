package com.example.mond.rx.screens.main_screen.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.mond.rx.App;
import com.example.mond.rx.R;
import com.example.mond.rx.common.BaseActivity;
import com.example.mond.rx.di.AppComponent;
import com.example.mond.rx.models.stores.Result;
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

    @BindView(R.id.btn_load)
    Button loadBtn;


    StoreAdapter mStoreAdapter;
    ProductsAdapter mProductsAdapter;

    List<Result> mStores = new ArrayList<>();
    List<com.example.mond.rx.models.products.Result> mProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mStoreRecycler.setLayoutManager(new LinearLayoutManager(this));
        mProductRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setupComponent(AppComponent appComponent) {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onDetach(this);
    }

    @OnClick(R.id.btn_load)
    public void getData() {
        if (mPresenter != null) {
            try {
                mPresenter.getData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setStores(Result store) {
        mStores.add(store);
        if (mStoreAdapter == null) {
            mStoreAdapter = new StoreAdapter(mStores);
        } else {
            mStoreAdapter.setNewStores(mStores);
        }
        mStoreRecycler.setAdapter(mStoreAdapter);
    }

    @Override
    public void setProducts(List<com.example.mond.rx.models.products.Result> products) {
        mProducts.addAll(products);
        if (mProductsAdapter == null) {
            mProductsAdapter = new ProductsAdapter(products);
        } else {
            mProductsAdapter.setNewProducts(mProducts);
        }
        mProductRecycler.setAdapter(mProductsAdapter);
    }
}
