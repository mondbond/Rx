package com.example.mond.rx.screens.main_screen.view;

import com.example.mond.rx.models.simple_models.Product;
import com.example.mond.rx.models.simple_models.Store;

public interface MainView {
    // TODO: 20/07/17 what about error handling?
    void setStore(Store store);

    void setProduct(Product product);
}
