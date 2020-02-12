package com.example.pmuny.simple_weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pmuny.simple_weather.API_Interface.API_Interface;
import com.example.pmuny.simple_weather.Model.Weather_Details;
import com.example.pmuny.simple_weather.Retrofit_Client.Retrofit_Client;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();
    TextInputEditText editText;
    TextView mCityLabel, mTemperature, mPressureLabel, mHumidityLabel, mSummary, mSuggestion;
    ImageView mImageIcon;
    String cityName;
    String units = "metric";
    String appId = "339d9ab1ba68682b093359f45076548e";

    API_Interface api_interface;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        editText = findViewById(R.id.editText);
        mCityLabel = findViewById(R.id.tvCityLabel);
        mTemperature = findViewById(R.id.tvTemperature);
        mPressureLabel = findViewById(R.id.tvPressureLabel);
        mHumidityLabel = findViewById(R.id.tvHumidityLabel);
        mSummary = findViewById(R.id.tvWeatherSummaryLabel);
        mSuggestion = findViewById(R.id.tvSuggestion);
        mImageIcon = findViewById(R.id.imageView);
        ConstraintLayout rootView = findViewById(R.id.rootView);
        cityName = getIntent().getStringExtra("city");
        //Initialize API
        Retrofit retrofit = Retrofit_Client.getInstance();
        api_interface = retrofit.create(API_Interface.class);
        //Create an Observer which receives Data from API
        final DisposableObserver<Weather_Details> observer = new DisposableObserver<Weather_Details>() {
            @Override
            public void onNext(Weather_Details weather_details) {
                mTemperature.setText(weather_details.getMain().getTemp() + "");
                mPressureLabel.setText(weather_details.getMain().getPressure() + " hPa");
                mHumidityLabel.setText(weather_details.getMain().getHumidity() + "%");
                mSummary.setText(weather_details.getWeather().get(0).getDescription() + "");

                //Setting the Suggestion & Icon according to what the weather condition is.
                mSuggestion.setText(viewModel.setWeatherSuggestion(weather_details.getWeather().get(0).getIcon()));
                mImageIcon.setImageResource(viewModel.setWeatherIcon(weather_details.getWeather().get(0).getIcon()));
                mCityLabel.setText(cityName);
            }

            @Override
            public void onError(Throwable e) {
                alertUserAboutError();
                mSuggestion.setText(e.getMessage());
                Log.d(TAG, e.getMessage() + "Error is here");
            }

            @Override
            public void onComplete() {

            }
        };

        if (isNetworkAvailable()) {
            disposable.add(
                    api_interface.getWeather_DetailsInfo(cityName, units, appId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(observer)
            );
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

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getSupportFragmentManager(), "Error_Dialog");
    }


    @Override
    protected void onStop() {
        disposable.clear();
        super.onStop();
    }
}

