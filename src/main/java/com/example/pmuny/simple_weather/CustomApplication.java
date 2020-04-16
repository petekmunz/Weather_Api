package com.example.pmuny.simple_weather;

import android.app.Application;

import com.example.pmuny.simple_weather.di.AppComponent;
import com.example.pmuny.simple_weather.di.DaggerAppComponent;

public class CustomApplication extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return component;
    }
}
