package com.example.pmuny.simple_weather.api_interface;

import com.example.pmuny.simple_weather.model.WeatherDetails;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Interface {

    @GET("weather")
    Single<WeatherDetails> getWeather_DetailsInfo(
            @Query("q") String city,
            @Query("units") String units,
            @Query("APPID") String appId
    );

}
