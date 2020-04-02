package ru.sultanyarov.catancounter.di;

import android.app.Application;

public class App extends Application {
    public static App INSTANCE;
    private static AppComponent component;

    public AppComponent getAppComponent() {
        if (component == null) {
            component = DaggerAppComponent.builder().build();
        }
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
    }
}