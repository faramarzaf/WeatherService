package com.google.yahooweather;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.yahooweather.raw.YahooWeatherModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    TextView resultTemp, resultCity, resultCountry, resultStatus, resultDate,
            resultWindSpeed, resultSunRise, resultSunSet, resultHumidity;
    EditText cityText;

    ImageView image_status, search_btn;
    public ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        bind();
        readyDialog();

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityText.length() == 0) {
                    cityText.setError("Field is empty...");
                } else {
                    dialog.show();
                    show(cityText.getText().toString());
                }
            }
        });


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
        resultDate = findViewById(R.id.resultDate);
        resultWindSpeed = findViewById(R.id.resultWindSpeed);
        resultSunRise = findViewById(R.id.resultSunRise);
        resultSunSet = findViewById(R.id.resultSunSet);
        resultHumidity = findViewById(R.id.resultHumidity);
        cityText = findViewById(R.id.cityText);
        image_status = findViewById(R.id.image_status);
        search_btn = findViewById(R.id.search_btn);
    }


    private void show(final String city) {

        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + city + "%2C%20ir%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Something is wrong", Toast.LENGTH_LONG).show();
                if (city.isEmpty()) {
                    cityText.setError("Field is empty...");
                }

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                dialog.show();
                parse(responseString);
                if (city.isEmpty()) {
                    cityText.setError("Field is empty...");
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                dialog.dismiss();
            }
        });
    }

    private void parse(String response) {
        Gson gson = new Gson();
        YahooWeatherModel yahooWeatherModel = gson.fromJson(response, YahooWeatherModel.class);
        String cityName = yahooWeatherModel.getQuery().getResults().getChannel().getLocation().getCity();
        String countryName = yahooWeatherModel.getQuery().getResults().getChannel().getLocation().getCountry();
        String temp = yahooWeatherModel.getQuery().getResults().getChannel().getItem().getCondition().getTemp();
        String status = yahooWeatherModel.getQuery().getResults().getChannel().getItem().getCondition().getText();
        String sunRise = yahooWeatherModel.getQuery().getResults().getChannel().getAstronomy().getSunrise();
        String sunSet = yahooWeatherModel.getQuery().getResults().getChannel().getAstronomy().getSunset();
        String humidity = yahooWeatherModel.getQuery().getResults().getChannel().getAtmosphere().getHumidity();
        String date = yahooWeatherModel.getQuery().getResults().getChannel().getLastBuildDate();
        String windSpeed = yahooWeatherModel.getQuery().getResults().getChannel().getWind().getSpeed();

        if (status.equals("Sunny")) {
            image_status.setImageResource(R.drawable.ic_sunny);
        }
        if (status.equals("Mostly Sunny")) {
            image_status.setImageResource(R.drawable.ic_mostly_sunny);
        }
        if (status.equals("Snowy")) {
            image_status.setImageResource(R.drawable.ic_snowy);
        }
        if (status.equals("Rainy")) {
            image_status.setImageResource(R.drawable.ic_rainy);
        }
        if (status.equals("Cloudy")) {
            image_status.setImageResource(R.drawable.ic_cloudy);
        }
        if (status.equals("Mostly Cloudy")) {
            image_status.setImageResource(R.drawable.ic_mostly_cloudy);
        }
        if (status.equals("Partly Cloudy")) {
            image_status.setImageResource(R.drawable.ic_partly_cloudy);
        }
        if (status.equals("Breezy")) {
            image_status.setImageResource(R.drawable.ic_breezy);
        }
        if (status.equals("Clear")) {
            image_status.setImageResource(R.drawable.ic_clear);
        }
        if (status.equals("Showers")) {
            image_status.setImageResource(R.drawable.ic_showers);
        }
        if (status.equals("Mostly Clear")) {
            image_status.setImageResource(R.drawable.ic_mostly_sunny);
        }
        if (status.equals("Rain And Snow")) {
            image_status.setImageResource(R.drawable.ic_snow_rain);
        }


        // temp conversion
        int f = Integer.parseInt(temp);
        double c = (f - 32) * 5 / 9;
        double fahren = (c * 9) / 5 + 32;

        // speed conversion
        int mph = Integer.parseInt(windSpeed);
        double kmh = mph * 1.609344;
        String KMH = String.format("%.2f", kmh);
        // km/h = mph x 1.609344

        resultCity.setText(cityName);
        resultCountry.setText(countryName);
        resultTemp.setText(c + " °C / " + fahren + " °F");
        resultDate.setText("Region time : " + date);
        resultSunRise.setText("Sunrise : " + sunRise);
        resultSunSet.setText("Sunset : " + sunSet);
        resultStatus.setText(status);
        resultHumidity.setText("Humidity : " + humidity + " %");
        resultWindSpeed.setText("Wind Speed : " + KMH + " Km/h");

    }
}