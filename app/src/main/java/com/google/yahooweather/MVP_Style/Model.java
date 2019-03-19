package com.google.yahooweather.MVP_Style;


import com.google.gson.Gson;
import com.google.yahooweather.models.ApixuWeatherModel;
import com.google.yahooweather.models.OpenWeatherMapModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class Model implements Contract.Model {
    Contract.Presenter presenter;

    @Override
    public void attachPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void search(String city) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=a5f06f7985166354304befe85a386554";
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                presenter.failed();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                parseMinMax(responseString);
            }
        });

    }

    private void parseMinMax(String responseString) {
        Gson gson = new Gson();
        OpenWeatherMapModel openWeatherMapModel = gson.fromJson(responseString, OpenWeatherMapModel.class);
        presenter.MinMaxTempReceived(openWeatherMapModel);
    }

    @Override
    public void search2(String city) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.apixu.com/v1/current.json?key=c4662836cc5848bfa8784116190903&q=" + city;
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                presenter.failed();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                parseMainData(responseString);
            }
        });


    }

    private void parseMainData(String responseString) {
        Gson gson = new Gson();
        ApixuWeatherModel apixuWeatherModel = gson.fromJson(responseString, ApixuWeatherModel.class);
        presenter.MainDataReceived(apixuWeatherModel);

    }

}
