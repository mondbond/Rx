package com.example.mond.rx.screens.main_screen.view;

import com.example.mond.rx.domain.models.Product;
import com.example.mond.rx.domain.models.Store;

import java.util.ArrayList;

public interface MainView {

    void setStore(ArrayList<Store> stores);

    void setProduct(ArrayList<Product> products);

    void showError(String msg);
}
