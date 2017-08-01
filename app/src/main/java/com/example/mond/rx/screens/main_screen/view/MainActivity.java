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

        mStoreRecycler.setAdapter(mStoreAdapter);
        mProductRecycler.setAdapter(mProductsAdapter);

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
        // TODO: 01/08/17 data analyse should be in presenter, not view
        if (!mStores.isEmpty()) {
            mStores.clear();
            mPresenter.stopLoadingData();
            mStoreAdapter.clear();
        }
        mPresenter.setUpStores();
    }

    @OnClick(R.id.btn_load_products)
    public void getProductsData() {
        // TODO: 01/08/17 data analyse should be in presenter, not view
        if (!mProducts.isEmpty()) {
            mProducts.clear();
            mPresenter.stopLoadingData();
            mProductsAdapter.clear();
        }
        mPresenter.setUpProductsByStores(mStores);
    }

    @Override
    public void setStore(Store store) {
        mStores.add(store);
        mStoreAdapter.setNewStores(mStores);
    }

    @Override
    public void setProduct(Product product) {
        mProducts.add(product);
        mProductsAdapter.setNewProduct(mProducts);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
