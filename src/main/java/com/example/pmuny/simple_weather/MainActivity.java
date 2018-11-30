package com.example.pmuny.simple_weather;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.example.pmuny.simple_weather.API_Interface.API_Interface;
import com.example.pmuny.simple_weather.Model.Weather_Details;
import com.example.pmuny.simple_weather.Retrofit_Client.Retrofit_Client;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    String TAG = MainActivity.class.getSimpleName();
    TextInputEditText editText;
    TextView mCityLabel,mTemperature,mPressureLabel,mHumidityLabel,mSummary,mSuggestion;
    ImageView mImageIcon;
    int suggestion = R.string.blank;
    int iconId = R.drawable.weather_none_available;
    String cityName;
    String units = "metric";
    String appId = "339d9ab1ba68682b093359f45076548e";

    API_Interface api_interface;
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        mCityLabel = findViewById(R.id.tvCityLabel);
        mTemperature = findViewById(R.id.tvTemperature);
        mPressureLabel = findViewById(R.id.tvPressureLabel);
        mHumidityLabel = findViewById(R.id.tvHumidityLabel);
        mSummary = findViewById(R.id.tvWeatherSummaryLabel);
        mSuggestion = findViewById(R.id.tvSuggestion);
        mImageIcon = findViewById(R.id.imageView);
        cityName = getIntent().getStringExtra("city");
        //Initialize API
        Retrofit retrofit = Retrofit_Client.getInstance();
        api_interface = retrofit.create(API_Interface.class);
        //Create an Observer which receives Data from API
          final DisposableObserver<Weather_Details> observer = new DisposableObserver<Weather_Details>() {
            @Override
            public void onNext(Weather_Details weather_details) {
                mTemperature.setText(weather_details.getMain().getTemp()+"");
                mPressureLabel.setText(weather_details.getMain().getPressure()+" hPa");
                mHumidityLabel.setText(weather_details.getMain().getHumidity()+"%");
                mSummary.setText(weather_details.getWeather().get(0).getDescription()+"");

                //Setting the suggestion according to what the weather condition is.
                if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("01d")){
                    suggestion = R.string.clear_day;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("01n")){
                    suggestion = R.string.clear_night;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("10d")){
                    suggestion = R.string.rain;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("10n")){
                    suggestion = R.string.rain;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("13d")){
                    suggestion = R.string.snow;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("13n")){
                    suggestion = R.string.snow;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("11d")){
                    suggestion = R.string.sleet;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("11n")){
                    suggestion = R.string.sleet;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("50d")){
                    suggestion = R.string.fog;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("50n")){
                    suggestion = R.string.fog;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("03d")){
                    suggestion = R.string.clear_day;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("03n")){
                    suggestion = R.string.cloudy;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("02d")){
                    suggestion = R.string.partly_cloudy;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("02n")){
                    suggestion = R.string.partly_cloudy_night;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("04d")){
                    suggestion = R.string.cloudy;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("04n")){
                    suggestion = R.string.cloudy;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("09d")){
                    suggestion = R.string.rain;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("09n")) {
                    suggestion = R.string.rain;
                }
                //Setting the Weather Icon according to the prevailing weather conditions.
                if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("01d")){
                    iconId = R.drawable.weather_clear;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("01n")){
                    iconId = R.drawable.weather_clear_night;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("10d")){
                    iconId = R.drawable.weather_rain_day;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("10n")){
                    iconId = R.drawable.weather_rain_night;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("13d")){
                    iconId = R.drawable.weather_snow;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("13n")){
                    iconId = R.drawable.weather_snow;
                } else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("11d")){
                    iconId = R.drawable.weather_storm;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("11n")){
                    iconId = R.drawable.weather_storm_night;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("50d")){
                    iconId = R.drawable.weather_mist;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("50n")){
                    iconId = R.drawable.weather_mist;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("03d")){
                    iconId = R.drawable.weather_few_clouds;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("03n")){
                    iconId = R.drawable.weather_clouds_night;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("02d")){
                    iconId = R.drawable.weather_few_clouds;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("02n")){
                    iconId = R.drawable.weather_few_clouds_night;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("04d")){
                    iconId = R.drawable.weather_few_clouds;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("04n")){
                    iconId = R.drawable.weather_few_clouds_night;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("09d")){
                    iconId = R.drawable.weather_showers_day;
                }else if (weather_details.getWeather().get(0).getIcon().equalsIgnoreCase("09n")) {
                    iconId = R.drawable.weather_showers_night;
                }
                mSuggestion.setText(suggestion);
                mImageIcon.setImageResource(iconId);
                mCityLabel.setText(cityName);
            }

            @Override
            public void onError(Throwable e) {
                alertUserAboutError();
                mSuggestion.setText(e.getMessage());
                Log.i(TAG, e.getMessage()+"Error is here");
            }

            @Override
            public void onComplete() {

            }
        };

        if (isNetworkAvailable()){
            disposable.add(
                    api_interface.getWeather_DetailsInfo(cityName,units,appId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(observer)
            );
        } else {
            Toast.makeText(this, "Network is Unavailable!", Toast.LENGTH_LONG).show();
        }

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getSupportFragmentManager(),"Error_Dialog");
    }

    @Override
    protected void onStop() {
        disposable.clear();
        super.onStop();
    }
}
