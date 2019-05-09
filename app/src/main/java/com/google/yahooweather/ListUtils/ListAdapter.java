package com.google.yahooweather.ListUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.yahooweather.R;

import java.util.List;


public class ListAdapter extends BaseAdapter {

    Context mContext;
    List<CityModel> cityModels;

    public ListAdapter(Context mContext, List<CityModel> cityModels) {
        this.mContext = mContext;
        this.cityModels = cityModels;
    }

    @Override
    public int getCount() {
        return cityModels.size();
    }

    @Override
    public Object getItem(int position) {
        return cityModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_detail, parent, false);

        TextView txt_tempF = convertView.findViewById(R.id.txt_tempF);
        TextView txt_tempC = convertView.findViewById(R.id.txt_tempC);
        TextView txt_country = convertView.findViewById(R.id.txt_country);
        TextView txt_city = convertView.findViewById(R.id.txt_city);
        TextView txt_status = convertView.findViewById(R.id.txt_status);

        ImageButton btn_close = convertView.findViewById(R.id.btn_close);
        ImageView image_status = convertView.findViewById(R.id.image_status);

        txt_tempF.setText(String.valueOf(cityModels.get(position).getTempF()));
        txt_tempC.setText(String.valueOf(cityModels.get(position).getTempC()));

        txt_status.setText(cityModels.get(position).getWeatherStatus());
        txt_country.setText(cityModels.get(position).getCountry());
        txt_city.setText(cityModels.get(position).getCity());


        return convertView;

    }

}
