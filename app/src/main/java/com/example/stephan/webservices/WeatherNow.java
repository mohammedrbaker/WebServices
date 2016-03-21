package com.example.stephan.webservices;

/**
 * Show the current weather of a city.
 */
public class WeatherNow {
    //fields
    private String city;            // city name
    private String country;         // county code
    private double temp;            // temperature right now
    private String icon;            // weather icon of current weather
    private String description;     // description of the clouds


    /**
     * Initialize the weather right now.
     */
    public WeatherNow(String placeName, String countryCode, double placeTemp, String imageID, String airDescription){
        city = placeName;
        temp = placeTemp;
        country = countryCode;
        icon = imageID;
        description = airDescription;
    }

    // methods

    /**
     * Get the temperature
     */
    public String getTemp(){
        return Double.toString(temp);
    }

    /**
     * Get the city name
     */
    public String getCity(){
        return city;
    }

    /**
     * Get the country code
     */
    public String getCountryCode(){
        return country;
    }

    /**
     * Get the weather icon as a string
     */
    public String getIcon(){
        return icon;
    }

    /**
     * Get the air description
     */
    public String getAirStatus(){
        return description;
    }
}
