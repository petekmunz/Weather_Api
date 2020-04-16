package com.example.pmuny.simple_weather.di;

import com.example.pmuny.simple_weather.api_interface.API_Interface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @Singleton
    @Provides
    OkHttpClient getOkHttpCleint() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    API_Interface getApiInterface(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(API_Interface.class);
    }
}
