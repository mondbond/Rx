package com.example.mond.rx.screens.main_screen.view;

import com.example.mond.rx.domain.models.Product;
import com.example.mond.rx.domain.models.Store;

public interface MainView {

    void setStore(Store store);

    void setProduct(Product product);

    void showError(String msg);
}
