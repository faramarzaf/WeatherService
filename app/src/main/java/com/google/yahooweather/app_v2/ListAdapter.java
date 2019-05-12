package com.google.yahooweather.app_v2;

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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_detail,parent,false);

        ImageView image_status = convertView.findViewById(R.id.image_status);
        ImageButton btn_close = convertView.findViewById(R.id.btn_close);

        TextView txt_tempF = convertView.findViewById(R.id.txt_tempF);
        TextView txt_tempC = convertView.findViewById(R.id.txt_tempC);
        TextView txt_country = convertView.findViewById(R.id.txt_country);
        TextView txt_city = convertView.findViewById(R.id.txt_city);
        TextView txt_status = convertView.findViewById(R.id.txt_status);

        txt_city.setText(cityModels.get(position).getCityName());
        txt_country.setText(cityModels.get(position).getCountryName());
        txt_status.setText(cityModels.get(position).getStatus());
        txt_tempF.setText(String.valueOf(cityModels.get(position).getTempF()));
        txt_tempC.setText(String.valueOf(cityModels.get(position).getTempC()));

        return convertView;




    }
}
