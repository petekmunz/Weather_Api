package com.example.pmuny.simple_weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pmuny.simple_weather.alertDialogs.AlertDialogFragment;
import com.example.pmuny.simple_weather.alertDialogs.NotFoundAlertDialogFragment;
import com.example.pmuny.simple_weather.model.WeatherDetails;
import com.example.pmuny.simple_weather.di.AppComponent;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

@Singleton
public class Repository {

    private MutableLiveData<WeatherDetails> liveWeather;
    private CompositeDisposable compDisposable;
    private String appID = "YOUR APP ID HERE";

    @Inject
    Repository() {
        liveWeather = new MutableLiveData<>();
    }

    LiveData<WeatherDetails> getLiveWeather() {
        return liveWeather;
    }

    void getWeatherFromApi(String cityName, String units, AppComponent appComponent, MainActivity activity) {
        getCompDisposable().add(appComponent.getApiInterface().getWeather_DetailsInfo(cityName, units, appID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherDetails>() {
                    @Override
                    public void onSuccess(WeatherDetails weatherDetails) {
                        liveWeather.setValue(weatherDetails);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            alertUserNoCityFoundError(activity);
                        } else {
                            alertUserAboutError(activity);
                        }
                    }
                }));
    }

    private CompositeDisposable getCompDisposable() {
        if (compDisposable == null) {
            compDisposable = new CompositeDisposable();
        }
        return compDisposable;
    }

    void disposeObserver() {
        getCompDisposable().clear();
    }

    private void alertUserAboutError(MainActivity activity) {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(activity.getSupportFragmentManager(), "Error_Dialog");
    }

    private void alertUserNoCityFoundError(MainActivity activity) {
        NotFoundAlertDialogFragment dialog = new NotFoundAlertDialogFragment();
        dialog.show(activity.getSupportFragmentManager(), "NoCity_Dialog");
    }
}
