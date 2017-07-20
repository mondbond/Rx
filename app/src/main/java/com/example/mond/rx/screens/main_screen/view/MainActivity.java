package com.example.mond.rx.screens.main_screen.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.mond.rx.App;
import com.example.mond.rx.R;
import com.example.mond.rx.common.BaseActivity;
import com.example.mond.rx.di.AppComponent;
import com.example.mond.rx.models.simple_models.Product;
import com.example.mond.rx.models.simple_models.Store;
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

    List<Store> mStores = new ArrayList<>();
    List<Product> mProducts = new ArrayList<>();

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
        if(mStoreAdapter != null) {
            if (!mStores.isEmpty() || !mProducts.isEmpty()) {
                mPresenter.stopLoadingData();
                mStoreAdapter.clear();
                mProductsAdapter.clear();
            }
        }

        if (mPresenter != null) {
            try {
                mPresenter.setUpData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setStore(Store store) {
        mStores.add(store);
        if (mStoreAdapter == null) {
            mStoreAdapter = new StoreAdapter(mStores);
        } else {
            mStoreAdapter.setNewStores(mStores);
        }
        mStoreRecycler.setAdapter(mStoreAdapter);
    }

    @Override
    public void setProduct(Product product) {
        mProducts.add(product);
        if (mProductsAdapter == null) {
            mProductsAdapter = new ProductsAdapter(mProducts);
        } else {
            mProductsAdapter.setNewProduct(mProducts);
        }
        mProductRecycler.setAdapter(mProductsAdapter);
    }
}
