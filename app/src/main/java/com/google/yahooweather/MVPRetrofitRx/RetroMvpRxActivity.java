package com.google.yahooweather.MVPRetrofitRx;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.yahooweather.R;
import com.google.yahooweather.models.ApixuWeatherModel;
import com.google.yahooweather.models.OpenWeatherMapModel;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

public class RetroMvpRxActivity extends AppCompatActivity implements Contract.View {
    Contract.Presenter presenter = new Presenter();

    TextView resultTemp, resultCity, resultCountry, resultStatus,
            resultWindSpeed, resultHumidity, maxTemp, minTemp, regionDate;
    EditText cityText;
    ImageView image_status, search_btn, img_humidity, img_windSpeed, img_maxTemp, img_minTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retro_mvp_rx);
        presenter.attachView(this);
        bind();

        RxTextView.textChanges(cityText).debounce(700, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onChange);
    }


    private void onChange(CharSequence charSequence) {
        rest(charSequence.toString());
    }

    void rest(String word) {
        RxInterface face = RetrofitGenerator.createService(RxInterface.class);
        face.minmaxTemp(word, "a5f06f7985166354304befe85a386554")
                .subscribeOn(Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess2, this::onError, this::onComplete);

        face.getMainData(word, "c4662836cc5848bfa8784116190903")
                .subscribeOn(Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError, this::onComplete);

    }

    private void onSuccess2(OpenWeatherMapModel openWeatherMapModel) {
        minmaxTemp(openWeatherMapModel);
    }

    private void onSuccess(ApixuWeatherModel apixuWeatherModel) {
        mainData(apixuWeatherModel);
    }


    private void onComplete() {

    }

    private void onError(Throwable throwable) {
        failed();
    }


    private void bind() {
        resultTemp = findViewById(R.id.resultTemp);
        resultCity = findViewById(R.id.resultCity);
        resultCountry = findViewById(R.id.resultCountry);
        resultStatus = findViewById(R.id.resultStatus);
        regionDate = findViewById(R.id.regionDate);
        resultWindSpeed = findViewById(R.id.resultWindSpeed);
        resultHumidity = findViewById(R.id.resultHumidity);
        cityText = findViewById(R.id.cityText);
        image_status = findViewById(R.id.image_status);
        search_btn = findViewById(R.id.search_btn);
        maxTemp = findViewById(R.id.maxTemp);
        minTemp = findViewById(R.id.minTemp);
        img_humidity = findViewById(R.id.img_humidity);
        img_windSpeed = findViewById(R.id.img_windSpeed);
        img_maxTemp = findViewById(R.id.img_maxTemp);
        img_minTemp = findViewById(R.id.img_minTemp);
    }


    @Override
    public void failed() {
        Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void minmaxTemp(OpenWeatherMapModel openWeatherMapModel) {
        if (cityText.length() == 0) {
            resultTemp.setText("");
            resultCity.setText("");
            resultCountry.setText("");
            resultStatus.setText("");
            resultWindSpeed.setText("");
            resultHumidity.setText("");
            maxTemp.setText("");
            minTemp.setText("");
            regionDate.setText("");

            image_status.setVisibility(View.GONE);
            search_btn.setVisibility(View.GONE);
            img_humidity.setVisibility(View.GONE);
            img_windSpeed.setVisibility(View.GONE);
            img_maxTemp.setVisibility(View.GONE);
            img_minTemp.setVisibility(View.GONE);

        } else if (cityText.length() != 0) {

            Double maxTempKlv = openWeatherMapModel.getMain().getTempMax();
            Double minTempKlv = openWeatherMapModel.getMain().getTempMin();

            Double maxTempC = (maxTempKlv) - 273.15;
            Double minTempC = (minTempKlv) - 273.15;

            Double maxTempF = (maxTempC * 9) / 5 + 32;
            Double minTempF = (minTempC * 9) / 5 + 32;

            String maxTempC2 = String.format("%.0f", maxTempC);
            String minTempC2 = String.format("%.0f", minTempC);

            String maxTempF2 = String.format("%.0f", maxTempF);
            String minTempF2 = String.format("%.0f", minTempF);

            maxTemp.setText(maxTempC2 + " °C / " + maxTempF2 + " °F");
            minTemp.setText(minTempC2 + " °C / " + minTempF2 + " °F");
            maxTemp.setTextColor(Color.RED);
            minTemp.setTextColor(Color.BLUE);

        }
    }

    @Override
    public void mainData(ApixuWeatherModel apixuWeatherModel) {
        if (cityText.length() == 0) {
            resultTemp.setText("");
            resultCity.setText("");
            resultCountry.setText("");
            resultStatus.setText("");
            resultWindSpeed.setText("");
            resultHumidity.setText("");
            maxTemp.setText("");
            minTemp.setText("");
            regionDate.setText("");

            image_status.setVisibility(View.GONE);
            search_btn.setVisibility(View.GONE);
            img_humidity.setVisibility(View.GONE);
            img_windSpeed.setVisibility(View.GONE);
            img_maxTemp.setVisibility(View.GONE);
            img_minTemp.setVisibility(View.GONE);

        } else if (cityText.length() != 0) {
            String localTime = apixuWeatherModel.getLocation().getLocaltime();
            String status = apixuWeatherModel.getCurrent().getCondition().getText();
            String countryName = apixuWeatherModel.getLocation().getCountry();
            String cityName = apixuWeatherModel.getLocation().getName();

            Double tempC = apixuWeatherModel.getCurrent().getTempC();
            Double tempF = apixuWeatherModel.getCurrent().getTempF();
            Double humidity = apixuWeatherModel.getCurrent().getHumidity();

            Double windKmh = apixuWeatherModel.getCurrent().getWindKph();
            //  Double windMph = apixuWeatherModel.getCurrent().getWindMph();

            String tempFahren = String.format("%.0f", tempF);
            String tempCenti = String.format("%.0f", tempC);
            String humidity2 = String.format("%.0f", humidity);

            regionDate.setText("Local time : " + localTime);
            resultStatus.setText(status);
            resultCountry.setText(countryName);
            resultCity.setText(cityName);
            resultTemp.setText(tempCenti + " °C / " + tempFahren + " °F");
            resultHumidity.setText(humidity2 + " %");
            resultWindSpeed.setText(+windKmh + " kmh   ");
            img_humidity.setVisibility(View.VISIBLE);
            img_windSpeed.setVisibility(View.VISIBLE);
            img_maxTemp.setVisibility(View.VISIBLE);
            img_minTemp.setVisibility(View.VISIBLE);


            if (status.equals("Sunny")) {
                image_status.setImageResource(R.drawable.ic_sunny);
            }
            if (status.equals("Cloudy")) {
                image_status.setImageResource(R.drawable.ic_cloudy);
            }
            if (status.equals("Partly cloudy")) {
                image_status.setImageResource(R.drawable.ic_partly_cloudy);
            }
            if (status.equals("Clear")) {
                image_status.setImageResource(R.drawable.ic_clear);

            }
            if (status.equals("Mist")) {
                image_status.setImageResource(R.drawable.mist);
            }
            if (status.equals("Overcast")) {
                image_status.setImageResource(R.drawable.ic_cloudy);
            }
            if (status.equals("Patchy rain possible")) {
                image_status.setImageResource(R.drawable.ic_rainy);
            }
            if (status.equals("Patchy snow possible")) {
                image_status.setImageResource(R.drawable.ic_snowy);
            }
            if (status.equals("Patchy sleet possible")) {
                image_status.setImageResource(R.drawable.sleet);
            }
            if (status.equals("Patchy freezing drizzle possible")) {
                image_status.setImageResource(R.drawable.freezingdrizzle);
            }
            if (status.equals("Thundery outbreaks possible")) {
                image_status.setImageResource(R.drawable.thunderoutbreaks);
            }
            if (status.equals("Blowing snow")) {
                image_status.setImageResource(R.drawable.blowingsnow);
            }
            if (status.equals("Blizzard")) {
                image_status.setImageResource(R.drawable.blizzard);
            }
            if (status.equals("Fog")) {
                image_status.setImageResource(R.drawable.mist);
            }
            if (status.equals("Freezing fog")) {
                image_status.setImageResource(R.drawable.freezingfog);
            }
            if (status.equals("Patchy light drizzle")) {
                image_status.setImageResource(R.drawable.freezingdrizzle);
            }
            if (status.equals("Light drizzle")) {
                image_status.setImageResource(R.drawable.freezingdrizzle);
            }
            if (status.equals("Freezing drizzle")) {
                image_status.setImageResource(R.drawable.freezingdrizzle);
            }
            if (status.equals("Heavy freezing drizzle")) {
                image_status.setImageResource(R.drawable.ic_showers);
            }
            if (status.equals("Patchy light rain")) {
                image_status.setImageResource(R.drawable.lightrain);
            }
            if (status.equals("Light rain")) {
                image_status.setImageResource(R.drawable.lightrain);
            }
            if (status.equals("Moderate rain at times")) {
                image_status.setImageResource(R.drawable.lightrain);
            }
            if (status.equals("Moderate rain")) {
                image_status.setImageResource(R.drawable.ic_rainy);
            }
            if (status.equals("Heavy rain at times")) {
                image_status.setImageResource(R.drawable.heavyrain);
            }
            if (status.equals("Heavy rain")) {
                image_status.setImageResource(R.drawable.heavyrain);
            }
            if (status.equals("Light freezing rain")) {
                image_status.setImageResource(R.drawable.ic_rainy);
            }
            if (status.equals("Moderate or heavy freezing rain")) {
                image_status.setImageResource(R.drawable.heavyrain);
            }
            if (status.equals("Moderate or heavy sleet")) {
                image_status.setImageResource(R.drawable.heavyrain);
            }
            if (status.equals("Patchy light snow")) {
                image_status.setImageResource(R.drawable.lightsnow);
            }
            if (status.equals("Light snow")) {
                image_status.setImageResource(R.drawable.lightsnow);
            }
            if (status.equals("Patchy moderate snow")) {
                image_status.setImageResource(R.drawable.moderatesnow);
            }
            if (status.equals("Moderate snow")) {
                image_status.setImageResource(R.drawable.moderatesnow);
            }
            if (status.equals("Patchy heavy snow")) {
                image_status.setImageResource(R.drawable.heavysnow);
            }
            if (status.equals("Heavy snow")) {
                image_status.setImageResource(R.drawable.heavysnow);
            }
            if (status.equals("Ice pellets")) {
                image_status.setImageResource(R.drawable.icepellets);
            }
            if (status.equals("Moderate or heavy rain shower")) {
                image_status.setImageResource(R.drawable.heavyrain);
            }
            if (status.equals("Torrential rain shower")) {
                image_status.setImageResource(R.drawable.heavyrain);
            }
            if (status.equals("Light sleet showers")) {
                image_status.setImageResource(R.drawable.lightrain);
            }
            if (status.equals("Moderate or heavy sleet showers")) {
                image_status.setImageResource(R.drawable.heavyrain);
            }
            if (status.equals("Light snow showers")) {
                image_status.setImageResource(R.drawable.lightsnow);
            }
            if (status.equals("Moderate or heavy snow showers")) {
                image_status.setImageResource(R.drawable.heavysnow);
            }
            if (status.equals("Light showers of ice pellets")) {
                image_status.setImageResource(R.drawable.icepellets);
            }
            if (status.equals("Moderate or heavy showers of ice pellets")) {
                image_status.setImageResource(R.drawable.heavyice);
            }
            if (status.equals("Patchy light rain with thunder")) {
                image_status.setImageResource(R.drawable.rainwiththunder);
            }
            if (status.equals("Moderate or heavy rain with thunder")) {
                image_status.setImageResource(R.drawable.heavyrainthunder);
            }
            if (status.equals("Patchy light snow with thunder")) {
                image_status.setImageResource(R.drawable.snowwiththunder);
            }
            if (status.equals("Moderate or heavy snow with thunder")) {
                image_status.setImageResource(R.drawable.heavysnowwiththunder);
            }

        }


    }


}
