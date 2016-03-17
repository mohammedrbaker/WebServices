package com.example.stephan.webservices;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {

    TagAsyncTask asyncTask;
    EditText userSearch;
    ListView listView;
    WeatherAdapter adapter;
    ArrayList<WeatherNow> allCitys;

    /**
     * On startup
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSearch = (EditText) findViewById(R.id.userSearch);
        listView = (ListView) findViewById(R.id.listViewWeather);

        allCitys = new ArrayList<>();

        readItemData();

        adapter = new WeatherAdapter(this, allCitys);

        listView.setAdapter(adapter);
    }

    /**
     * search for weather
     */
    public void searchWeather(View view){
        String search = userSearch.getText().toString();
        if(!search.isEmpty()){
            search = search.replace(" ", "");
            asyncTask = new TagAsyncTask(this);
            asyncTask.execute(search);
            userSearch.setText("");
        }
        else{
            Toast.makeText(MainActivity.this, "Please enter a place", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Show data received from server.
     */
    public void processFinish(String output){
        JSONObject jsonObject;
        try {
            // make a jsonObject
            jsonObject = new JSONObject(output);

            // placename
            String place = jsonObject.getString("name");
            //country
            String country = jsonObject.getJSONObject("sys").getString("country");
            // temparature
            Double temparature = jsonObject.getJSONObject("main").getDouble("temp");
            // the picture to show
            String iconid = ((JSONObject) jsonObject.getJSONArray("weather").get(0)).getString("icon");
            // description of the air.
            String airDescription = ((JSONObject) jsonObject.getJSONArray("weather").get(0)).getString("description");

            WeatherNow newCity = new WeatherNow(place,country, temparature, iconid, airDescription);

            allCitys.add(0, newCity);
            if(allCitys.size() > 5){
                allCitys.remove(5);
            }

            writeItemData();
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Read all items on ONE list.
     * See writeItemData to know how it is stored.
     */
    public void readItemData(){
        try {
            allCitys.clear();
            // Open File
            Scanner scan = new Scanner(openFileInput("PARENTFILE"));

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

                    allCitys.add(new WeatherNow(city,countryCode, temp, id, description));
                }
            }

            // done
            scan.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeItemData(){
        // now write data.
        try {
            // open/create
            PrintStream out = new PrintStream(openFileOutput("PARENTFILE", Context.MODE_PRIVATE));

            // add all items
            for( int i = 0; i < allCitys.size(); i++){
                WeatherNow thisItem = allCitys.get(i);

                out.println(thisItem.getCity() + "," +
                        thisItem.getCountryCode() + "," + thisItem.getTemp() + "," +
                        thisItem.getIcon() + "," + thisItem.getAirStatus());
            }

            // close file
            out.close();

        } catch (Throwable t) {
            // error happened.
            t.printStackTrace();
            Toast.makeText(this, "An Error Occurred: "+ t.toString(), Toast.LENGTH_LONG).show();

        }
    }
}