package com.google.yahooweather.app_v2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.yahooweather.R;

public class DetailActivity extends AppCompatActivity {

    public static final String KEY_CITY = "city";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_STATUS = "status";
    public static final String KEY_TEMPC = "tempc";
    public static final String KEY_TEMPF = "tempf";


    TextView resultTemp, resultCity, resultCountry, resultStatus,
            resultWindSpeed, resultHumidity, maxTemp, minTemp, regionDate;
    ImageView image_status, img_humidity, img_windSpeed, img_maxTemp, img_minTemp;
    View line;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bind();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String city = bundle.getString(KEY_CITY);
            String country = bundle.getString(KEY_COUNTRY);
            String status = bundle.getString(KEY_STATUS);
            double tempC = bundle.getDouble(KEY_TEMPC);
            double tempF = bundle.getDouble(KEY_TEMPF);

            resultCity.setText(city);
            resultCountry.setText(country);
            resultStatus.setText(status);
            resultTemp.setText(tempC + " °C / " + tempF + " °F");
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    void bind() {
        resultTemp = findViewById(R.id.resultTemp);
        resultCity = findViewById(R.id.resultCity);
        resultCountry = findViewById(R.id.resultCountry);
        resultStatus = findViewById(R.id.resultStatus);
        regionDate = findViewById(R.id.regionDate);
        resultWindSpeed = findViewById(R.id.resultWindSpeed);
        resultHumidity = findViewById(R.id.resultHumidity);
        image_status = findViewById(R.id.image_status);
        maxTemp = findViewById(R.id.maxTemp);
        minTemp = findViewById(R.id.minTemp);
        line = findViewById(R.id.line);
        img_humidity = findViewById(R.id.img_humidity);
        img_windSpeed = findViewById(R.id.img_windSpeed);
        img_maxTemp = findViewById(R.id.img_maxTemp);
        img_minTemp = findViewById(R.id.img_minTemp);

    }
}
