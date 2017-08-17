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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mStoreAdapter = new StoreAdapter(null);
        mProductsAdapter = new ProductsAdapter(null);

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
        mPresenter.setUpStores();
    }

    @OnClick(R.id.btn_load_products)
    public void getProductsData() {
        mPresenter.setUpProductsByStores();
    }

    @Override
    public void setStore(List<Store> stores) {
        mStoreAdapter.setNewStores(stores);
    }

    @Override
    public void setProduct(List<Product> products) {
        mProductsAdapter.setNewProduct(products);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showStoresLoadingError() {
        Toast.makeText(this, "Stores loading error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProductsLoadingError() {
        Toast.makeText(this, "Products loading error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMissingInternetError() {
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }
}
