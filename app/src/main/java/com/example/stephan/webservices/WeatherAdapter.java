package com.example.stephan.webservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Stephan on 15-3-2016.
 */
public class WeatherAdapter extends ArrayAdapter<WeatherNow>{

    Context context;
    ArrayList<WeatherNow> weather;

    public WeatherAdapter(Context contextOfApp, ArrayList<WeatherNow> weatherCity){
        super(contextOfApp, R.layout.single_row_weather, weatherCity);

        context = contextOfApp;
        weather = weatherCity;
    }

    /**
     * Initialize View.
     * Set an OnClick on ImageButton, an OnClick on the View and an OnLongClick on the View.
     */
    public View getView(final int position, View view, ViewGroup parent){
        if(view == null){
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_row_weather, parent, false);
        }

        // find Views on ListView.
        final TextView weatherInfo = (TextView) view.findViewById(R.id.infoWeather);
        final ImageView weatherIcon = (ImageView) view.findViewById(R.id.imageWeather);

        WeatherNow thisWeather = weather.get(position);

        String temp = thisWeather.getTemp();
        String place = thisWeather.getCity();
        String country = thisWeather.getCountryCode();
        String icon = "image" + thisWeather.getIcon();
        String airDescription = thisWeather.getAirStatus();
        int iconID = getImage(icon);

        String text = "Temp: " + temp + "\n" + "Place: " +
                place + ", " + country + "\n" + "Air: " + airDescription;


        // add values to Views.
        weatherIcon.setImageResource(iconID);
        weatherInfo.setText(text);

        // Done.
        return view;
    }

    private int getImage(String icon){
        switch (icon){
            case "image01d":
                return R.drawable.image01d;
            case "image01n":
                return R.drawable.image01n;
            case "image02d":
                return R.drawable.image02d;
            case "image02n":
                return R.drawable.image02n;
            case "image03d":
                return R.drawable.image03d;
            case "image03n":
                return R.drawable.image03n;
            case "image04d":
                return R.drawable.image04d;
            case "image04n":
                return R.drawable.image04n;
            case "image09d":
                return R.drawable.image09d;
            case "image09n":
                return R.drawable.image09n;
            case "image10d":
                return R.drawable.image10d;
            case "image10n":
                return R.drawable.image10n;
            case "image11d":
                return R.drawable.image11d;
            case "image11n":
                return R.drawable.image11n;
            case "image13d":
                return R.drawable.image13d;
            case "image13n":
                return R.drawable.image13n;
            case "image50d":
                return R.drawable.image50d;
            case "image50n":
                return R.drawable.image50n;
            default:
                return R.drawable.image50n;

        }
    }


}
