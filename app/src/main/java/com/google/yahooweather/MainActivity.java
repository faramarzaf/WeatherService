package com.google.yahooweather;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.yahooweather.models.ApixuWeatherModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    TextView resultTemp, resultCity, resultCountry, resultStatus, resultDate,
            resultWindSpeed, resultHumidity;
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

        resultHumidity = findViewById(R.id.resultHumidity);
        cityText = findViewById(R.id.cityText);
        image_status = findViewById(R.id.image_status);
        search_btn = findViewById(R.id.search_btn);
    }


    private void show(final String city) {
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
        ApixuWeatherModel apixuWeatherModel = gson.fromJson(response, ApixuWeatherModel.class);
        String cityName = apixuWeatherModel.getLocation().getName();
        String countryName = apixuWeatherModel.getLocation().getCountry();

        Double tempF = apixuWeatherModel.getCurrent().getTempF();
        String tempFahren = String.format("%.0f", tempF);

        Double tempC = Double.valueOf(apixuWeatherModel.getCurrent().getTempC());
        String tempCenti = String.format("%.0f", tempC);

       /* Double tempC = (tempF - 32) * 5 / 9;
        String tempCenti = String.format("%.0f", tempC);*/

        String status = apixuWeatherModel.getCurrent().getCondition().getText();
        Integer humidity = apixuWeatherModel.getCurrent().getHumidity();
        String date = apixuWeatherModel.getLocation().getLocaltime();
        Double windSpeed = apixuWeatherModel.getCurrent().getWindKph();

        Integer errorMsg = apixuWeatherModel.getCurrent().getCondition().getCode();
        if (errorMsg.equals(1006)) {
            Toast.makeText(this, "No matching location found", Toast.LENGTH_SHORT).show();
        }


        if (status.equals("Sunny")) {
            image_status.setImageResource(R.drawable.ic_sunny);
        }
        if (status.equals("Mostly sunny")) {
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
        resultTemp.setText(tempCenti +" °C / "+tempFahren + " °F");
        resultDate.setText("Region time : " + date);
        resultStatus.setText(status);
        resultHumidity.setText("Humidity : " + humidity + " %");
        resultWindSpeed.setText("Wind Speed : " + windSpeed + " Km/h");
    }
}