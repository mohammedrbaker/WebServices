package com.example.stephan.webservices;

/**
 * Created by Stephan on 15-3-2016.
 */
public class WeatherNow {
    //fields
    private String city;
    private String country;
    private double temp;
    private String icon;
    private String description;


    // constructor
    public WeatherNow(String placeName, String countryCode, double placeTemp, String imageID, String airDescription){
        city = placeName;
        temp = placeTemp;
        country = countryCode;
        icon = imageID;
        description = airDescription;
    }

    // methods
    public String getTemp(){
        return Double.toString(temp);
    }

    public String getCity(){
        return city;
    }

    public String getCountryCode(){
        return country;
    }

    public String getIcon(){
        return icon;
    }

    public String getAirStatus(){
        return description;
    }
}
