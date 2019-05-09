package com.google.yahooweather.ListUtils;

public class CityModel {

    private String country, city, weatherStatus;
    private double tempF, tempC;


    public CityModel(String country, String city, String weatherStatus, double tempF, double tempC) {
        this.country = country;
        this.city = city;
        this.weatherStatus = weatherStatus;
        this.tempF = tempF;
        this.tempC = tempC;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(String weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public double getTempF() {
        return tempF;
    }

    public void setTempF(double tempF) {
        this.tempF = tempF;
    }

    public double getTempC() {
        return tempC;
    }

    public void setTempC(double tempC) {
        this.tempC = tempC;
    }
}
