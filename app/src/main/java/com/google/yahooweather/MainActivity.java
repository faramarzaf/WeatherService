package com.google.yahooweather;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.yahooweather.models.ApixuWeatherModel;
import com.google.yahooweather.models.OpenWeatherMapModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity {

    TextView resultTemp, resultCity, resultCountry, resultStatus,
            resultWindSpeed, resultHumidity, maxTemp, minTemp, regionDate;

    EditText cityText;
    View line;
    ImageView image_status, search_btn, img_humidity, img_windSpeed, img_maxTemp, img_minTemp;
    public ProgressDialog dialog;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        bind();
        readyDialog();
        swipeRefreshOff();
        search_btn.setOnClickListener(v -> {
            if (cityText.length() == 0) {
                cityText.setError("Field is empty...");
                swipeRefreshOff();
            } else {
                dialog.show();
                showData(cityText.getText().toString());
                showNewData(cityText.getText().toString());
                swipeRefreshFun();
            }
        });

    }

    void swipeRefreshFun() {
        swipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.rgb(255, 191, 0), Color.rgb(61, 182, 24));
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(true);
            new Handler().postDelayed(() -> {
                swipeRefresh.setRefreshing(false);
                Toasty.success(this, "Refreshed!", Toast.LENGTH_SHORT, true).show();
            }, 4400);
        });

    }

    void swipeRefreshOff() {
        swipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE, Color.rgb(255, 191, 0), Color.rgb(61, 182, 24));
        swipeRefresh.setOnRefreshListener(() -> {
            swipeRefresh.setRefreshing(true);
            new Handler().postDelayed(() -> {
                swipeRefresh.setRefreshing(false);

            }, 0);
        });
    }

    private void showNewData(final String city) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=a5f06f7985166354304befe85a386554";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //Toast.makeText(MainActivity.this, "Data Not Found !", Toast.LENGTH_LONG).show();
                if (city.isEmpty()) {
                    cityText.setError("Field is empty...");
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                swipeRefreshFun();
                dialog.show();
                parseNewData(responseString);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });

    }

    private void parseNewData(String response) {
        Gson gson = new Gson();
        OpenWeatherMapModel openWeatherMapModel = gson.fromJson(response, OpenWeatherMapModel.class);

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

    private void showData(final String city) {
        String url = "https://api.apixu.com/v1/current.json?key=c4662836cc5848bfa8784116190903&q=" + city;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toasty.error(MainActivity.this, "Data Not Found", Toast.LENGTH_SHORT, true).show();
                if (city.isEmpty()) {
                    cityText.setError("Field is empty...");
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                swipeRefreshFun();

                dialog.show();
                parseData(responseString);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });

    }

    private void parseData(String response) {
        Gson gson = new Gson();
        ApixuWeatherModel apixuWeatherModel = gson.fromJson(response, ApixuWeatherModel.class);
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
        line.setVisibility(View.VISIBLE);
        // String tempFahren = String.format("%.0f", tempF);
        //[°C] = [K] − ۲۷۳.۱۵
        // 1m/s = 3.6*km/h
        // 1km/h = 1m/s / 3.6

        // temp conversion
      /*  int f = Integer.parseInt(temp);
        double c = (f - 32) * 5 / 9;
        double fahren = (c * 9) / 5 + 32;*/

       /* speed conversion
        int mph = Integer.parseInt(windSpeed);
        double kmh = mph * 1.609344;
        String KMH = String.format("%.2f", kmh);
        km/h = mph x 1.609344*/

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

    void readyDialog() {
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading!");
        dialog.setMessage("Please wait...");
    }

    void bind() {
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

        line = findViewById(R.id.line);

        img_humidity = findViewById(R.id.img_humidity);
        img_windSpeed = findViewById(R.id.img_windSpeed);
        img_maxTemp = findViewById(R.id.img_maxTemp);
        img_minTemp = findViewById(R.id.img_minTemp);
        swipeRefresh = findViewById(R.id.swipeRefresh);
    }

}