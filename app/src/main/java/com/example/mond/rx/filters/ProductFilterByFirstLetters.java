package com.example.mond.rx.filters;

import com.example.mond.rx.models.products.Result;

public class ProductFilterByFirstLetters implements ProductFilter {

    private int mCount;
    private String mCondition;

    public ProductFilterByFirstLetters(int mCount, String condition) {
        this.mCount = mCount;
        mCondition = condition;
    }

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String mCondition) {
        this.mCondition = mCondition;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public boolean isAppropriate(Result result) {

        char[] nameByChar = result.getName().toCharArray();
        char[] conditionByChar = mCondition.toCharArray();

        for (int i = 0; i < mCondition.length(); ++i) {
            if (!String.valueOf(nameByChar[i]).equals(String.valueOf(conditionByChar[i]))) {
                return false;
            }
        }
        return true;
    }
}
