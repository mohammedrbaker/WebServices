package com.example.stephan.webservices;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * An adapter to show the weather forecast per day.
 *
 * Requires an arraylist of WeatherDays
 */
public class WeatherDayAdapter extends ArrayAdapter<WeatherDays> {

    Context context;                    // show content here
    ArrayList<WeatherDays> weather;     // the items

    /**
     * Initialize adapter
     */
    public WeatherDayAdapter(Context contextOfApp, ArrayList<WeatherDays> weatherCity){
        super(contextOfApp, R.layout.single_row_horizontal_weather, weatherCity);

        context = contextOfApp;
        weather = weatherCity;
    }

    /**
     * Initialize View.
     */
    public View getView(final int position, View view, ViewGroup parent){
        if(view == null){
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_row_horizontal_weather, parent, false);
        }

        // find all textviews
        final TextView date = (TextView) view.findViewById(R.id.date);
        final TextView min = (TextView) view.findViewById(R.id.min);
        final TextView max = (TextView) view.findViewById(R.id.max);
        final TextView day = (TextView) view.findViewById(R.id.day);
        final TextView night = (TextView) view.findViewById(R.id.night);
        final TextView eve = (TextView) view.findViewById(R.id.eve);

        // set all information
        WeatherDays weatherDays = weather.get(position);
        date.setText(weatherDays.getDate());
        min.setText(("min: " + weatherDays.getTempMin()));
        max.setText(("max: " + weatherDays.getTempMax()));
        day.setText(("day: " + weatherDays.getTempDay()));
        night.setText(("night: " + weatherDays.getTempNight()));
        eve.setText(("eve: " + weatherDays.getTempEve()));

        // Done.
        return view;
    }
}
