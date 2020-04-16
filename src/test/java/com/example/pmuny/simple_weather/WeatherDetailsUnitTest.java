package com.example.pmuny.simple_weather;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * WeatherDetails local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class WeatherDetailsUnitTest {
    private Repository repo;

    @Before
    public void initializeRepo() {
        repo = new Repository();
    }

    @Test
    public void correctIcon_isReturned() {
        MainViewModel viewModel = new MainViewModel(repo);
        assertThat(viewModel.setWeatherIcon("11n"), is(R.drawable.weather_storm_night));
    }

    @Test
    public void correctSuggestion_isReturned() {
        MainViewModel viewModel = new MainViewModel(repo);
        assertThat(viewModel.setWeatherSuggestion("11n"), is(R.string.sleet));
    }
}