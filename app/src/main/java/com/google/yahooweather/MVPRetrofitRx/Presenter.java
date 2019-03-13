package com.google.yahooweather.MVPRetrofitRx;

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
    public void failed() {
        view.failed();
    }

    @Override
    public void search(String word) {
        model.search(word);
    }

    @Override
    public void cityFound(ApixuWeatherModel apixuWeatherModel, OpenWeatherMapModel openWeatherMapModel) {
        view.cityFound(apixuWeatherModel,openWeatherMapModel);
    }
}
