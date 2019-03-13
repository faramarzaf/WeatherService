package com.google.yahooweather.MVPRetrofitRx;

public class Model implements Contract.Model {

    private Contract.Presenter presenter;


    @Override
    public void attachPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void search(String word) {

    }
}
