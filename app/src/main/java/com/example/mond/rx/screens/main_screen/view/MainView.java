package com.example.mond.rx.screens.main_screen.view;

import com.example.mond.rx.models.stores.Result;

import java.util.ArrayList;
import java.util.List;

public interface MainView {
    public void setStores(Result stores);
    public void setProducts (List<com.example.mond.rx.models.products.Result> products);
}
