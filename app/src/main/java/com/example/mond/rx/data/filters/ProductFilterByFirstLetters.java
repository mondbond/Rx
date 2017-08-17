package com.example.mond.rx.data.filters;

import com.example.mond.rx.domain.ProductFilter;
import com.example.mond.rx.domain.models.Product;

public class ProductFilterByFirstLetters implements ProductFilter {

    private String mCondition;

    public ProductFilterByFirstLetters(String condition) {
        mCondition = condition;
    }

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String mCondition) {
        this.mCondition = mCondition;
    }

    @Override
    public boolean isAppropriate(Product product) {
        return product.getName().startsWith(mCondition);
    }
}
