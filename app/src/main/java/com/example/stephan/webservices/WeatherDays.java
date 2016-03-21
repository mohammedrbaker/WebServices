package com.example.stephan.webservices;

/**
 * Weather forecast for one day.
 */
public class WeatherDays {
    private String date;        // the day
    private String tempMax;     // max temp of the day
    private String tempMin;     // min temp of the day
    private String tempDay;     // temp in the day
    private String tempNight;   // temp in the night
    private String tempEve;     // temp in the evening

    /**
     * initialize the day
     */
    public WeatherDays(String datum, String max, String min, String day, String night, String eve){
        date = datum;
        tempMax = max;
        tempMin = min;
        tempDay = day;
        tempNight = night;
        tempEve = eve;
    }

    /**
     * Get the day
     */
    public String getDate(){
        return date;
    }

    /**
     * Get the temp max
     */
    public String getTempMax(){
        return tempMax;
    }

    /**
     * Get the temp min
     */
    public String getTempMin(){
        return tempMin;
    }

    /**
     * Get the temp in the day
     */
    public String getTempDay(){
        return tempDay;
    }

    /**
     * Get the temp in the night
     */
    public String getTempNight(){
        return tempNight;
    }

    /**
     * Get the temp in the evening
     */
    public String getTempEve(){
        return tempEve;
    }
}
