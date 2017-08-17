package com.example.mond.rx.data.filters;

import com.example.mond.rx.domain.StoreFilter;
import com.example.mond.rx.domain.models.Store;

public class StoreFilterByFirstLetters implements StoreFilter {

    private String mCondition;

    public StoreFilterByFirstLetters(String condition) {
        mCondition = condition;
    }

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String mCondition) {
        this.mCondition = mCondition;
    }

    @Override
    public boolean isAppropriate(Store store) {
        return store.getName().startsWith(mCondition);
    }
}
