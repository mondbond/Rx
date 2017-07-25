package com.example.mond.rx.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mond.rx.app.App;
import com.example.mond.rx.di.AppComponent;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(App.getAppComponent());
    }

    public abstract void setupComponent(AppComponent appComponent);
}