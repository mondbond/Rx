package com.example.mond.rx.di;

import com.example.mond.rx.App;
import com.example.mond.rx.screens.main_screen.view.MainActivity;

import dagger.Component;

@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(MainActivity mainActivity);
}
