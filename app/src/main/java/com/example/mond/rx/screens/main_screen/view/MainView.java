package com.example.mond.rx.screens.main_screen.view;

import com.example.mond.rx.domain.models.Product;
import com.example.mond.rx.domain.models.Store;

import java.util.List;

public interface MainView {

    void setStore(List<Store> stores);

    void setProduct(List<Product> products);

    void showError(String msg);

    void showStoresLoadingError();

    void showProductsLoadingError();

    void showMissingInternetError();
}
