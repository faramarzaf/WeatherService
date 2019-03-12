package com.google.yahooweather.MVPRetrofitRx;

import com.google.yahooweather.models.ApixuWeatherModel;
import com.google.yahooweather.models.OpenWeatherMapModel;

public interface Contract {

    interface View{
        void failed();
        void cityFound(ApixuWeatherModel apixuWeatherModel, OpenWeatherMapModel openWeatherMapModel);
    }
    interface Presenter{
        void attachView(View view);
        void failed();
        void search(String word);
        void cityFound(ApixuWeatherModel apixuWeatherModel, OpenWeatherMapModel openWeatherMapModel);

    }

    interface Model{
        void attachPresenter(Presenter presenter);
        void search(String word);
    }
}
