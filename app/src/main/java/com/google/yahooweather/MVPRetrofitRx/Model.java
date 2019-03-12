package com.google.yahooweather.MVPRetrofitRx;

public class Model implements Contract.Model {

    private Contract.Presenter presenter;
    ServiceInterface webInterface = RetroGenerator.createService(ServiceInterface.class);

    @Override
    public void attachPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void search(String word) {

    }
}
