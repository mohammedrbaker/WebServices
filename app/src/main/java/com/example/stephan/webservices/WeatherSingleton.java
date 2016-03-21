package com.example.stephan.webservices;

import android.content.Context;
import android.widget.Toast;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Singleton to save and load data from file.
 */
public class WeatherSingleton {
    // fields
    final private String fileSaveLoc = "PARENTFILE";   // filename where weather info now are saved
    private ArrayList<WeatherNow> weatherNow;          // Weather info now
    Context context;                                   // Know where to save and load files.

    // Initialize to null so you can give Context.
    private static WeatherSingleton instance = null;

    /**
     * Initialize Singleton with context.
     */
    public static WeatherSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new WeatherSingleton(new ArrayList<WeatherNow>(), context);
        }
        return instance;
    }

    // constructor
    private WeatherSingleton(ArrayList<WeatherNow> newWeatherNow, Context contextMain){
        weatherNow = newWeatherNow;
        context = contextMain;
    }

    /**
     * Return all WeatherNow
     */
    public ArrayList<WeatherNow> getWeatherNow(){
        return weatherNow;
    }

    /**
     * Return a single WeatherNow
     */
    public WeatherNow getSingleWeather(int position){
        return weatherNow.get(position);
    }

    /**
     * Add an weather. Cant be larger then 5
     */
    public void add(WeatherNow newWeatherNow){
        weatherNow.add(0, newWeatherNow);

        // cant be larger than 5
        if(weatherNow.size() > 5){
            weatherNow.remove(5);
        }
    }

    /**
     * Read all items on ONE list.
     * See writeItemData to know how it is stored.
     */
    public void readDataFromFile(){
        try {
            weatherNow.clear();
            // Open File
            Scanner scan = new Scanner(context.openFileInput(fileSaveLoc));

            // Find all To Do items
            while (scan.hasNextLine()) {
                String line = scan.nextLine();

                // if line is not empty it is data, add item.
                if (!line.isEmpty()) {
                    // first is the item name and then the item status.
                    String[] readList = line.split(",");
                    String city = readList[0];
                    String countryCode = readList[1];
                    double temp = Double.parseDouble(readList[2]);
                    String id = readList[3];
                    String description = readList[4];

                    weatherNow.add(new WeatherNow(city,countryCode, temp, id, description));
                }
            }

            // done
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void writeDataOnFile(){
        // now write data.
        try {
            // open/create
            PrintStream out = new PrintStream(context.openFileOutput(fileSaveLoc, Context.MODE_PRIVATE));

            // add all items
            for( int i = 0; i < weatherNow.size(); i++){
                WeatherNow thisItem = weatherNow.get(i);

                out.println(thisItem.getCity() + "," +
                        thisItem.getCountryCode() + "," + thisItem.getTemp() + "," +
                        thisItem.getIcon() + "," + thisItem.getAirStatus());
            }

            // close file
            out.close();

        } catch (Throwable t) {
            // error happened.
            t.printStackTrace();
            Toast.makeText(context, "An Error Occurred: " + t.toString(), Toast.LENGTH_LONG).show();

        }
    }
}
