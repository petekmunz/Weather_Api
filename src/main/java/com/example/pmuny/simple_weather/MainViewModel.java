package com.example.pmuny.simple_weather;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public MainViewModel() {
    }

    int setWeatherIcon(String weatherIcon) {
        int iconId = R.drawable.weather_none_available;
        switch (weatherIcon) {
            case "01d":
                iconId = R.drawable.weather_clear;
                break;
            case "01n":
                iconId = R.drawable.weather_clear_night;
                break;
            case "10d":
                iconId = R.drawable.weather_rain_day;
                break;
            case "10n":
                iconId = R.drawable.weather_rain_night;
                break;
            case "13d":
            case "13n":
                iconId = R.drawable.weather_snow;
                break;
            case "11d":
                iconId = R.drawable.weather_storm;
                break;
            case "11n":
                iconId = R.drawable.weather_storm_night;
                break;
            case "50d":
            case "50n":
                iconId = R.drawable.weather_mist;
                break;
            case "03d":
            case "02d":
            case "04d":
                iconId = R.drawable.weather_few_clouds;
                break;
            case "03n":
            case "02n":
            case "04n":
                iconId = R.drawable.weather_few_clouds_night;
                break;
            case "09d":
                iconId = R.drawable.weather_showers_day;
                break;
            case "09n":
                iconId = R.drawable.weather_showers_night;
                break;
        }

        return iconId;
    }

    int setWeatherSuggestion(String weatherIcon) {
        int suggestion = R.string.blank;
        switch (weatherIcon) {
            case "01d":
            case "03d":
                suggestion = R.string.clear_day;
                break;
            case "01n":
                suggestion = R.string.clear_night;
                break;
            case "10d":
            case "10n":
            case "09d":
            case "09n":
                suggestion = R.string.rain;
                break;
            case "13d":
            case "13n":
                suggestion = R.string.snow;
                break;
            case "11d":
            case "11n":
                suggestion = R.string.sleet;
                break;
            case "50d":
            case "50n":
                suggestion = R.string.fog;
                break;
            case "02d":
                suggestion = R.string.partly_cloudy;
                break;
            case "04d":
            case "03n":
            case "04n":
                suggestion = R.string.cloudy;
                break;
            case "02n":
                suggestion = R.string.partly_cloudy_night;
                break;
        }

        return suggestion;
    }
}
