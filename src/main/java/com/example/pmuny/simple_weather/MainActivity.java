package com.example.pmuny.simple_weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pmuny.simple_weather.model.WeatherDetails;
import com.example.pmuny.simple_weather.di.ActivityComponent;
import com.example.pmuny.simple_weather.di.AppComponent;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private TextView mCityLabel, mTemperature, mPressureLabel, mHumidityLabel, mSummary, mSuggestion;
    private ImageView mImageIcon;
    private String cityName;

    @Inject
    MyViewModelFactory viewModelFactory;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Inject activity first.
        AppComponent appComponent = ((CustomApplication) getApplication()).getAppComponent();
        ActivityComponent activityComponent = appComponent.getActivityComponent().create();
        activityComponent.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);
        mCityLabel = findViewById(R.id.tvCityLabel);
        mTemperature = findViewById(R.id.tvTemperature);
        mPressureLabel = findViewById(R.id.tvPressureLabel);
        mHumidityLabel = findViewById(R.id.tvHumidityLabel);
        mSummary = findViewById(R.id.tvWeatherSummaryLabel);
        mSuggestion = findViewById(R.id.tvSuggestion);
        mImageIcon = findViewById(R.id.imageView);
        ConstraintLayout rootView = findViewById(R.id.rootView);
        ConstraintLayout viewContainer = findViewById(R.id.viewContainer);
        cityName = getIntent().getStringExtra("city");

        //Observe for LiveData
        viewModel.getWeatherLive().observe(this, weatherDetails -> {
            if (weatherDetails != null && weatherDetails.getName().equalsIgnoreCase(cityName)) {
                viewContainer.setVisibility(View.VISIBLE);
                updateUI(weatherDetails);
            }
        });

        if (isNetworkAvailable()) {
            viewModel.getWeatherFromApi(cityName, "metric", appComponent, this);
        } else {
            Snackbar snackbar = Snackbar.make(rootView, "Network is Unavailable", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void updateUI(WeatherDetails weatherDetails) {
        mTemperature.setText(weatherDetails.getMain().getTemp() + "");
        mPressureLabel.setText(weatherDetails.getMain().getPressure() + " hPa");
        mHumidityLabel.setText(weatherDetails.getMain().getHumidity() + "%");
        mSummary.setText(weatherDetails.getWeather().get(0).getDescription() + "");

        //Setting the Suggestion & Icon according to what the weather condition is.
        mSuggestion.setText(viewModel.setWeatherSuggestion(weatherDetails.getWeather().get(0).getIcon()));
        mImageIcon.setImageResource(viewModel.setWeatherIcon(weatherDetails.getWeather().get(0).getIcon()));
        mCityLabel.setText(cityName);
    }

    @Override
    protected void onStop() {
        viewModel.disposeObserver();
        super.onStop();
    }
}

