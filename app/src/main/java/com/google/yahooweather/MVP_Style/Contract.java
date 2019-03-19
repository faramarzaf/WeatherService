package com.google.yahooweather.MVP_Style;

import com.google.yahooweather.models.ApixuWeatherModel;
import com.google.yahooweather.models.OpenWeatherMapModel;

public interface Contract {

    interface View{
        void failed();
        void MinMaxTempReceived(OpenWeatherMapModel openWeatherMapModel);
        void MainDataReceived(ApixuWeatherModel apixuWeatherModel);
    }
    interface Presenter{
        void search(String city);
        void search2(String city);
        void MinMaxTempReceived(OpenWeatherMapModel openWeatherMapModel);
        void MainDataReceived(ApixuWeatherModel apixuWeatherModel);
        void attachView(View view);
        void failed();

    }

    interface Model{
        void attachPresenter(Presenter presenter);
        void search(String city);
        void search2(String city);
    }
}
