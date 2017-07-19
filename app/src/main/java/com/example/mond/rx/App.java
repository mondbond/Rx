package com.example.mond.rx;

import android.app.Application;

import com.example.mond.rx.di.AppComponent;
import com.example.mond.rx.di.AppModule;
import com.example.mond.rx.di.DaggerAppComponent;

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
