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
import com.google.yahooweather.models.OpenWeatherMapModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    TextView resultTemp, resultCity, resultCountry, resultStatus,
            resultWindSpeed, resultHumidity, maxTemp,minTemp;

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

        resultWindSpeed = findViewById(R.id.resultWindSpeed);
        resultHumidity = findViewById(R.id.resultHumidity);
        cityText = findViewById(R.id.cityText);
        image_status = findViewById(R.id.image_status);
        search_btn = findViewById(R.id.search_btn);
        maxTemp = findViewById(R.id.maxTemp);
        minTemp = findViewById(R.id.minTemp);
    }

    private void show(final String city) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=a5f06f7985166354304befe85a386554";
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
        OpenWeatherMapModel openWeatherMapModel = gson.fromJson(response, OpenWeatherMapModel.class);
        String cityName = openWeatherMapModel.getName();
        String countryName = openWeatherMapModel.getSys().getCountry();

        Double tempK = openWeatherMapModel.getMain().getTemp();
        Double tempCenti = (tempK) - 273.15;
        String tempCenti2 = String.format("%.0f", tempCenti);

        Double tempFahren = (tempCenti * 9) / 5 + 32;
        String tempFahren2 = String.format("%.0f", tempFahren);

        Double tempMax = openWeatherMapModel.getMain().getTempMax();
        Double tempMin = openWeatherMapModel.getMain().getTempMin();
        Double tempCentiMax = (tempMax) - 273.15;
        Double tempCentiMin = (tempMin) - 273.15;
        String tempCentiMax2 = String.format("%.0f", tempCentiMax);
        String tempCentiMin2 = String.format("%.0f", tempCentiMin);


        Double tempFahrenMax = (tempCentiMax * 9) / 5 + 32;
        Double tempFahrenMin = (tempCentiMin * 9) / 5 + 32;

        String tempFahrenMax2 = String.format("%.0f", tempFahrenMax);
        String tempFahrenMin2 = String.format("%.0f", tempFahrenMin);

        String status = openWeatherMapModel.getWeather().get(0).getMain();
        Integer humidity = openWeatherMapModel.getMain().getHumidity();

        Double windSpeed = openWeatherMapModel.getWind().getSpeed();
        Double windKmh = (windSpeed) / 3.6;
        String windKmh2 = String.format("%.2f", windKmh);

        Integer errorMsg = openWeatherMapModel.getCod();
        if (!errorMsg.equals(200)) {
            Toast.makeText(this, "No matching location found", Toast.LENGTH_SHORT).show();
        }

        // String tempFahren = String.format("%.0f", tempF);
        //[°C] = [K] − ۲۷۳.۱۵
        // 1m/s = 3.6*km/h
        // 1km/h = 1m/s / 3.6

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
        if (status.equals("Clouds")) {
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

        // temp conversion
      /*  int f = Integer.parseInt(temp);
        double c = (f - 32) * 5 / 9;
        double fahren = (c * 9) / 5 + 32;*/

       /* speed conversion
        int mph = Integer.parseInt(windSpeed);
        double kmh = mph * 1.609344;
        String KMH = String.format("%.2f", kmh);
        km/h = mph x 1.609344*/

        resultCity.setText(cityName);
        resultCountry.setText(countryName);
        resultTemp.setText(tempCenti2 + " °C / " + tempFahren2 + " °F");
        resultStatus.setText(status);
        resultHumidity.setText("Humidity : " + humidity + " %");
        resultWindSpeed.setText("Wind Speed : " + windKmh2 + " Km/h   "+windSpeed +"m/s");
        maxTemp.setText("Max temperature : "+tempCentiMax2+ " °C / "+tempFahrenMax2+" °F" );
        minTemp.setText("Min temperature : "+tempCentiMin2+ " °C / "+tempFahrenMin2+" °F" );
    }
}