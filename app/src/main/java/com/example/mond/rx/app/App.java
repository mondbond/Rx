package com.example.mond.rx.app;

import android.app.Application;

import com.example.mond.rx.di.AppComponent;
import com.example.mond.rx.di.AppModule;
import com.example.mond.rx.di.DaggerAppComponent;

// TODO: 7/25/17 Single commit from 20.07.2017? That is not good.
public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        buildGraphAndInject();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public void buildGraphAndInject() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
    }
}
