package com.example.stephan.webservices;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Show current weather.
 */
public class MainActivity extends AppCompatActivity implements TagAsyncTask.AsyncResponse {

    TagAsyncTask asyncTask;                     // Make contact with the internet
    EditText userSearch;                        // Get the city the users wants
    ListView listView;                          // Listview to show adapter
    WeatherAdapter adapter;                     // Adapter to show weather
    WeatherSingleton weatherManager;            // Manage save and loading to/from file
    final String SEARCHMETHOD = "weather?q=";   // Search method for api
    ProgressDialog progressDialog;              // Wait for data

    /**
     * On startup
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userSearch = (EditText) findViewById(R.id.userSearch);
        listView = (ListView) findViewById(R.id.listViewWeather);

        weatherManager = WeatherSingleton.getInstance(this);

        weatherManager.readDataFromFile();

        adapter = new WeatherAdapter(this, weatherManager.getWeatherNow());

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent advancedActivity = new Intent(MainActivity.this, SecondActivity.class);
                advancedActivity.putExtra("searchKey", weatherManager.getWeatherNow().get(position).getCity());
                startActivity(advancedActivity);
            }
        });

    }

    /**
     * Response from asynctask to set tell users they must be patient.
     */
    public void updateProcess(String updateProcess){
        // Remove dialog if there
        if(progressDialog != null){
            progressDialog.dismiss();
        }
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(updateProcess);
        progressDialog.show();
    }

    /**
     * search for weather
     */
    public void searchWeather(View view){
        String search = userSearch.getText().toString();
        if(!search.isEmpty()){
            search = search.replace(" ", "");
            asyncTask = new TagAsyncTask(this);
            asyncTask.execute(search, SEARCHMETHOD);
            userSearch.setText("");
        }
        else{
            Toast.makeText(MainActivity.this, R.string.emptySearchMessage, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * When asyncTask is finished, this function will be called. This function will recieve the data
     * from the api-call. It will be a String in jsonformat. It will take all information and put
     * it inside an new WeatherDay and add that WeatherDay to the arraylist.
     *
     * If an error happened result will contain the error message.
     */
    public void processFinish(String result){
        // Remove dialog
        if(progressDialog != null){
            progressDialog.dismiss();
        }

        // get the WeatherNow from json
        try {
            // make a jsonObject
            JSONObject jsonObject = new JSONObject(result);

            // Get all information from json
            String place = jsonObject.getString("name");
            String country = jsonObject.getJSONObject("sys").getString("country");
            Double temparature = jsonObject.getJSONObject("main").getDouble("temp");
            String iconid = ((JSONObject) jsonObject.getJSONArray("weather").get(0)).getString("icon");
            String airDescription = ((JSONObject) jsonObject.getJSONArray("weather").get(0)).getString("description");

            // Make new WeatherNow
            WeatherNow newCity = new WeatherNow(place,country, temparature, iconid, airDescription);

            // Only keep track of 5 items.
            weatherManager.add(newCity);

            // Save data and update.
            weatherManager.writeDataOnFile();
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}