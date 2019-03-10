package com.google.yahooweather;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.yahooweather.models.ApixuWeatherModel;
import com.google.yahooweather.models.OpenWeatherMapModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    TextView resultTemp, resultCity, resultCountry, resultStatus,
            resultWindSpeed, resultHumidity, maxTemp, minTemp, regionDate;

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
                    showData(cityText.getText().toString());
                    showNewData(cityText.getText().toString());
                }
            }
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
                dialog.show();
                parseNewData(responseString);
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

        maxTemp.setText("Max temperature : " + maxTempC2 + " °C / " + maxTempF2 + " °F");
        minTemp.setText("Min temperature : " + minTempC2 + " °C / " + minTempF2 + " °F");
    }

    private void showData(final String city) {
        String url = "https://api.apixu.com/v1/current.json?key=c4662836cc5848bfa8784116190903&q=" + city;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Data Not Found !", Toast.LENGTH_LONG).show();
                if (city.isEmpty()) {
                    cityText.setError("Field is empty...");
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
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
        Double windMph = apixuWeatherModel.getCurrent().getWindMph();

        String tempFahren = String.format("%.0f", tempF);
        String tempCenti = String.format("%.0f", tempC);
        String humidity2 = String.format("%.0f", humidity);

        regionDate.setText("Local time : " + localTime);
        resultStatus.setText(status);
        resultCountry.setText(countryName);
        resultCity.setText(cityName);
        resultTemp.setText(tempCenti + " °C / " + tempFahren + " °F");
        resultHumidity.setText("Humidity : " + humidity2 + " %");
        resultWindSpeed.setText("Wind Speed : " + windKmh + " kmh   " + windMph + " mph");


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
        if (status.equals("Mostly sunny")) {
            image_status.setImageResource(R.drawable.ic_mostly_sunny);
        }
        if (status.equals("Snow")) {
            image_status.setImageResource(R.drawable.ic_snowy);
        }
        if (status.equals("Rain")) {
            image_status.setImageResource(R.drawable.ic_rainy);
        }
        if (status.equals("Cloudy")) {
            image_status.setImageResource(R.drawable.ic_cloudy);
        }
        if (status.equals("Mostly cloudy")) {
            image_status.setImageResource(R.drawable.ic_mostly_cloudy);
        }
        if (status.equals("Partly cloudy")) {
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
        if (status.equals("Mostly clear")) {
            image_status.setImageResource(R.drawable.ic_mostly_sunny);
        }
        if (status.equals("Rain and snow")) {
            image_status.setImageResource(R.drawable.ic_snow_rain);
        }
        if (status.equals("Mist")) {
            image_status.setImageResource(R.drawable.mist);
        }
        if (status.equals("Haze")) {
            image_status.setImageResource(R.drawable.mist);
        }
        if (status.equals("Overcast")) {
            image_status.setImageResource(R.drawable.mist);
        }
        if (status.equals("Patchy rain possible")) {
            image_status.setImageResource(R.drawable.mist);
        }
        if (status.equals("Patchy snow possible")) {
            image_status.setImageResource(R.drawable.mist);
        }
        if (status.equals("Patchy sleet possible")) {
            image_status.setImageResource(R.drawable.mist);
        }
        if (status.equals("Patchy freezing drizzle possible")) {
            image_status.setImageResource(R.drawable.mist);
        }
        if (status.equals("Thundery outbreaks possible")) {
            image_status.setImageResource(R.drawable.mist);
        }
        if (status.equals("Blowing snow")) {
            image_status.setImageResource(R.drawable.mist);
        }
        if (status.equals("Blizzard")) {
            image_status.setImageResource(R.drawable.mist);
        }
        if (status.equals("Fog")) {
            image_status.setImageResource(R.drawable.mist);
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
    }

}