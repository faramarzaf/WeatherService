package com.google.yahooweather.app_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.yahooweather.ListUtils.CityModel;
import com.google.yahooweather.ListUtils.ListAdapter;
import com.google.yahooweather.R;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    ImageView search_btn;
    EditText cityText;

    ListView detail_list;
    List<CityModel> cityModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bind();

        cityModels = new ArrayList<>();

        CityModel c1 = new CityModel("Iran", "Tehran", "sunny", 15, 25);
        CityModel c2 = new CityModel("Turkey", "Istanbul", "rainy", 35, 25);
        CityModel c3 = new CityModel("iran", "arak", "sunny", 45, 25);
        CityModel c4 = new CityModel("Canada", "Calgary", "snowy", 31, 27);
        CityModel c5 = new CityModel("iran", "shiraz", "sunny", 45, 24);
        CityModel c6 = new CityModel("USA", "New york", "windy", 35, 25);
        CityModel c7 = new CityModel("iran", "tehran", "sunny", 24, 31);

        cityModels.add(c1);
        cityModels.add(c2);
        cityModels.add(c3);
        cityModels.add(c4);
        cityModels.add(c5);
        cityModels.add(c6);
        cityModels.add(c7);

        ListAdapter listAdapter = new ListAdapter(this, cityModels);
        detail_list.setAdapter(listAdapter);


        detail_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Main2Activity.this, cityModels.get(position).getCity(), Toast.LENGTH_LONG).show();

            }

        });

    }

    void bind() {
        detail_list = findViewById(R.id.detail_list);
        search_btn = findViewById(R.id.search_btn);
        cityText = findViewById(R.id.cityText);

    }

}
