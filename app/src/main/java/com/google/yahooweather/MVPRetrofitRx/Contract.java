package com.google.yahooweather.MVPRetrofitRx;

import com.google.yahooweather.models.ApixuWeatherModel;
import com.google.yahooweather.models.OpenWeatherMapModel;

public interface Contract {

    interface View{
        void failed();
        void minmaxTemp(OpenWeatherMapModel openWeatherMapModel);
        void mainData(ApixuWeatherModel apixuWeatherModel);
    }
    interface Presenter{
        void attachView(View view);
        void failed();
        void search(String word);
        void minmaxTemp(OpenWeatherMapModel openWeatherMapModel);
        void mainData(ApixuWeatherModel apixuWeatherModel);

    }

    interface Model{
        void attachPresenter(Presenter presenter);
        void search(String word);
    }
}
