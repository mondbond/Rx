package com.example.mond.rx.data.filters;

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
