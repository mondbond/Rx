package com.example.mond.rx.data.filters;

import com.example.mond.rx.data.models.stores.Result;
import com.example.mond.rx.domain.models.Store;

public class StoreFilterByFirstLetters implements StoreFilter {

    private int mCount;
    private String mCondition;

    public StoreFilterByFirstLetters(int mCount, String condition) {
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
    public boolean isAppropriate(Store store) {

        char[] nameByChar = store.getName().toCharArray();
        char[] conditionByChar = mCondition.toCharArray();

        for (int i = 0; i < mCondition.length(); ++i) {
            if (!String.valueOf(nameByChar[i]).equals(String.valueOf(conditionByChar[i]))) {
                return false;
            }
        }
        return true;
    }
}
