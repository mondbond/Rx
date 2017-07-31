package com.example.mond.rx.data.filters;

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

        char[] nameByChar = product.getName().toCharArray();
        char[] conditionByChar = mCondition.toCharArray();

        for (int i = 0; i < mCondition.length(); ++i) {
            if (!String.valueOf(nameByChar[i]).equals(String.valueOf(conditionByChar[i]))) {
                return false;
            }
        }
        return true;
    }
}
