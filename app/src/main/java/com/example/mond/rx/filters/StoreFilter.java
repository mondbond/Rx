package com.example.mond.rx.filters;

/**
 * Created by mond on 18.07.17.
 */

public interface StoreFilter {

    int getCount();

    boolean isApropriative(String example);
}
