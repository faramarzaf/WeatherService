package com.google.yahooweather.app_v2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.yahooweather.R;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    ListView detail_list;
    List<CityModel> cityModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bind();

        cityModels = new ArrayList<>();

        CityModel c1 = new CityModel("Iran","Tehran","Sunny",25.5,22);
        CityModel c2 = new CityModel("Iran","Shiraz","Snowy",35.5,8);
        CityModel c3 = new CityModel("Iran","Esfahan","Rainy",25.5,8);
        CityModel c4 = new CityModel("Iran","Amol","Sunny",25,4);
        CityModel c5 = new CityModel("Iran","Babol","Snowy",15.5,3);
        CityModel c6 = new CityModel("Iran","Kerman","Rainy",13,12);
        CityModel c7 = new CityModel("Iran","Tabriz","Sunny",23,10);
        CityModel c8 = new CityModel("Iran","Qom","Snowy",5,-5);
        CityModel c9 = new CityModel("Iran","Arak","Rainy",13,7);

        cityModels.add(c1);
        cityModels.add(c2);
        cityModels.add(c3);
        cityModels.add(c4);
        cityModels.add(c5);
        cityModels.add(c6);
        cityModels.add(c7);
        cityModels.add(c8);
        cityModels.add(c9);

        ListAdapter listAdapter = new ListAdapter(this,cityModels);

        detail_list.setAdapter(listAdapter);

        detail_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(Main2Activity.this,DetailActivity.class);
                intent.putExtra(DetailActivity.KEY_CITY, cityModels.get(position).getCityName());
                intent.putExtra(DetailActivity.KEY_COUNTRY, cityModels.get(position).getCountryName());
                intent.putExtra(DetailActivity.KEY_STATUS, cityModels.get(position).getStatus());
                intent.putExtra(DetailActivity.KEY_TEMPC, cityModels.get(position).getTempC());
                intent.putExtra(DetailActivity.KEY_TEMPF, cityModels.get(position).getTempF());

               startActivity(intent);
            }
        });
    }

    void bind(){
        detail_list = findViewById(R.id.detail_list);
    }



}
