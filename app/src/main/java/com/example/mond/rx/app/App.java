package com.example.mond.rx.app;

import android.app.Application;

import com.example.mond.rx.di.AppComponent;
import com.example.mond.rx.di.AppModule;
import com.example.mond.rx.di.DaggerAppComponent;
import com.example.mond.rx.di.NetworkModule;

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
                .networkModule(new NetworkModule())
                .build();
        appComponent.inject(this);
    }
}
