package com.google.yahooweather.app_v2;

public class CityModel {

     String countryName, cityName, status;
     double TempC, TempF;

    public CityModel(){

    }

    public CityModel(String countryName, String cityName, String status, double tempC, double tempF) {
        this.countryName = countryName;
        this.cityName = cityName;
        this.status = status;
        TempC = tempC;
        TempF = tempF;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTempC() {
        return TempC;
    }

    public void setTempC(double tempC) {
        TempC = tempC;
    }

    public double getTempF() {
        return TempF;
    }

    public void setTempF(double tempF) {
        TempF = tempF;
    }
}
