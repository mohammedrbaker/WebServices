package com.example.stephan.webservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An request helper for the Open Weather API.
 * It requires a String search: a city and a String userTag is the kind of information u want.
 * Information about api: http://openweathermap.org/api
 */
public class HttpRequestHelper {

    private static final String url1 = "http://api.openweathermap.org/data/2.5/";   // url
    private static final String key = "&APPID=4b6bdf453a3547d80f28075d55ad6b51";    // key
    private static final String method = "&units=Metric";                           // use Celsius

    /**
     * The HttpRequistHelper
     */
    protected static synchronized String downloadFromServer(String search, String userTag){

        // Initialize
        String returnValue = "";

        // Get complete url
        String compeleteUrl = url1 + userTag + search + method + key;

        // Initialize url to null
        URL url = null;
        try {
            url = new URL(compeleteUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // make connection.
        HttpURLConnection connection;
        if(url != null){

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // read response
                Integer response = connection.getResponseCode();

                // handle errors
                if(response >= 200 && response < 300){
                    // read the data
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while((line = br.readLine()) != null){
                        returnValue = returnValue + line;
                    }

                }
                // get error
                else{
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    String line;
                    while((line = br.readLine()) != null){
                        returnValue = returnValue + line;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return returnValue;
    }

}

