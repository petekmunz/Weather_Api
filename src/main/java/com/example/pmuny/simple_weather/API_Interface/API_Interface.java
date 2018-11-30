package com.example.pmuny.simple_weather.API_Interface;

import com.example.pmuny.simple_weather.Model.Weather_Details;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Interface {

    @GET("weather")
    Observable<Weather_Details> getWeather_DetailsInfo(
            @Query("q")String city,
            @Query("units")String units,
            @Query("APPID")String appId
    );
}
