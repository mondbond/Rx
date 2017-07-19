package com.example.mond.rx.common;

public interface BasePresenter<T> {
    void onAttach(T view);
    void onDetach(T view);
}
