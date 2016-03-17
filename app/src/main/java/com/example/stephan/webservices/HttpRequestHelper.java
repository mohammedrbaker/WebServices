package com.example.stephan.webservices;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Stephan on 14-3-2016.
 */
public class HttpRequestHelper {
    private static final String url1 = "http://api.openweathermap.org/data/2.5/";
    private static final String key = "&APPID=4b6bdf453a3547d80f28075d55ad6b51";
    private static final String method = "&units=Metric";

    protected static synchronized String downloadFromServer(String search){

        // initialize.
        String returnValue = "";

        // get tag chosen by user.
        String userTag = "weather?q=";

        userTag += search;

        // get compelete url from strings
        String compeleteUrl = url1 + userTag + method + key;
        Log.v("url", compeleteUrl);

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
                if(response >= 200 && response < 300){
                    // read the data
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while((line = br.readLine()) != null){
                        returnValue = returnValue + line;
                    }

                }
                else{
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    // show error code
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        return returnValue;
    }

}

