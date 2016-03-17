package com.example.stephan.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


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
        }
        else{
            Toast.makeText(MainActivity.this, "Please enter place", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Show data received from server.
     */
    public void processFinish(String output){
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(output);
            String place = jsonObject.getString("name");
            String country = jsonObject.getJSONObject("sys").getString("country");
            Double temparature = jsonObject.getJSONObject("main").getDouble("temp");
            String id = ((JSONObject) jsonObject.getJSONArray("weather").get(0)).getString("icon");
            String airDescription = ((JSONObject) jsonObject.getJSONArray("weather").get(0)).getString("description");
            Log.v("id", id);

            String text = "Place: " + place + "\n" + "Country: " + country + "\n" + "Temparature: " + temparature;

            WeatherNow newCity = new WeatherNow(place,country, temparature, id, airDescription);

            allCitys.add(newCity);
            adapter.notifyDataSetChanged();

            Log.v("adepter updated", "update");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}