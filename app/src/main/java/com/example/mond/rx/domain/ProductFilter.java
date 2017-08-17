package com.example.mond.rx.domain;

import com.example.mond.rx.domain.models.Product;

public interface ProductFilter {

    boolean isAppropriate(Product poduct);
}
