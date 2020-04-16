package com.example.pmuny.simple_weather.di;

import com.example.pmuny.simple_weather.MainActivity;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ViewModelModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);

    @Subcomponent.Factory
    interface Factory {
        ActivityComponent create();
    }
}
