package com.example.pmuny.simple_weather.di;

import com.example.pmuny.simple_weather.api_interface.API_Interface;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {RetrofitModule.class, SubcomponentsModule.class})
public interface AppComponent {
    ActivityComponent.Factory getActivityComponent();

    API_Interface getApiInterface();
}
