package com.example.mond.rx.filters;

import android.util.Log;

import com.example.mond.rx.models.stores.Result;

/**
 * Created by mond on 20.07.17.
 */

public class StoreFilterByFirstLetters implements StoreFilter{

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
    public boolean isAppropriate(Result result) {

        char[] examvleByChar = result.getName().toCharArray();
        char[] conditionByChar = mCondition.toCharArray();

        for(int i = 0; i < mCondition.length(); ++i) {
            if(!String.valueOf(examvleByChar[i]).equals(String.valueOf(conditionByChar[i]))) {
                return false;
            }
        }
        Log.d("Is Appropriate", result.getName() + " - " + mCondition);
        return true;
    }


}
