package com.google.yahooweather.MVPRetrofitRx;

import com.google.yahooweather.models.ApixuWeatherModel;
import com.google.yahooweather.models.OpenWeatherMapModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RxInterface {

    @GET("/")
    Observable<OpenWeatherMapModel> minmaxTemp(
            @Query("q") String word
            ,
            @Query("APPID") String key
    ) ;

    @GET("/")
    Observable<ApixuWeatherModel> getMainData(
            @Query("q") String word
            ,
            @Query("key") String key
    ) ;

}
