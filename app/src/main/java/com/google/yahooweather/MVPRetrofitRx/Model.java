package com.google.yahooweather.MVPRetrofitRx;

import android.support.annotation.NonNull;

import com.google.yahooweather.models.ApixuWeatherModel;
import com.google.yahooweather.models.OpenWeatherMapModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Model implements Contract.Model {

    private Contract.Presenter presenter;
    RxInterface webInterface = RetrofitGenerator.createService(RxInterface.class);
    RxInterface webInterface2 = RetrofitGenerator.createService2(RxInterface.class);

    @Override
    public void attachPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void search(String word) {
        webInterface.minmaxTemp(word, "a5f06f7985166354304befe85a386554").enqueue(new Callback<OpenWeatherMapModel>() {


            @Override
            public void onResponse(@NonNull Call<OpenWeatherMapModel> call, @NonNull Response<OpenWeatherMapModel> response) {
                presenter.minmaxTemp(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<OpenWeatherMapModel> call, @NonNull Throwable t) {
                presenter.failed();
            }
        });

        webInterface2.getMainData(word, "c4662836cc5848bfa8784116190903").enqueue(new Callback<ApixuWeatherModel>() {

            @Override
            public void onResponse(@NonNull Call<ApixuWeatherModel> call, @NonNull Response<ApixuWeatherModel> response) {
                presenter.mainData(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ApixuWeatherModel> call, @NonNull Throwable t) {
                presenter.failed();
            }
        });

    }
}
