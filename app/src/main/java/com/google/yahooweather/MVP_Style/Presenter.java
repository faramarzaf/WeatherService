package com.google.yahooweather.MVP_Style;

import com.google.yahooweather.models.ApixuWeatherModel;
import com.google.yahooweather.models.OpenWeatherMapModel;

public class Presenter implements Contract.Presenter {
    Contract.View view;
    Model model = new Model();

    @Override
    public void attachView(Contract.View view) {
        this.view = view;
        model.attachPresenter(this);
    }

    @Override
    public void search(String city) {
        model.search(city);
    }

    @Override
    public void search2(String city) {
        model.search2(city);
    }

    @Override
    public void MinMaxTempReceived(OpenWeatherMapModel openWeatherMapModel) {
        view.MinMaxTempReceived(openWeatherMapModel);
    }

    @Override
    public void MainDataReceived(ApixuWeatherModel apixuWeatherModel) {
        view.MainDataReceived(apixuWeatherModel);
    }



    @Override
    public void failed() {
        view.failed();
    }
}
