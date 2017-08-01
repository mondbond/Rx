package com.example.mond.rx.data.filters;

import com.example.mond.rx.domain.models.Product;

// TODO: 01/08/17 these filters don't related to 'data', should be in 'domain' instead or 
public interface ProductFilter {

    boolean isAppropriate(Product poduct);
}
