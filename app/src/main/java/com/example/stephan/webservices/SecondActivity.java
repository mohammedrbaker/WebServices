package com.example.stephan.webservices;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Show the 7 days weather forecast.
 */
public class SecondActivity extends AppCompatActivity implements TagAsyncTask.AsyncResponse {

    TagAsyncTask asyncTask;                             // Get api information
    ArrayList<WeatherDays> weatherDays;                 // All 7 days
    WeatherDayAdapter adapter;                          // Adapter for ListView
    ListView listView;                                  // ListView to show info.
    final String SEARCHMETHOD = "forecast/daily?q=";    // Search method for api
    ProgressDialog progressDialog;                      // Wait for data
    TextView cityTextView;                              // TextView for info and cityname.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Find ListView
        listView = (ListView) findViewById(R.id.listView);
        cityTextView = (TextView) findViewById(R.id.dailyInfo);

        // Get the place to search the 7 days weather forecast.
        String search = getIntent().getExtras().getString("searchKey");

        // make sure you found something.
        if (search != null) {
            // Show city name
            String cityText = search + getString(R.string.dailyWeather);
            cityTextView.setText(cityText);

            // Remove all spaces
            search = search.replace(" ", "");

            // Get information from api
            asyncTask = new TagAsyncTask(this);
            asyncTask.execute(search, SEARCHMETHOD);

            // Make adapter and ListView
            weatherDays = new ArrayList<>();
            adapter = new WeatherDayAdapter(this, weatherDays);
            listView.setAdapter(adapter);
        }
        else{
            // When u didn't get a city to search for
            Toast.makeText(SecondActivity.this, R.string.errorMessage, Toast.LENGTH_SHORT).show();
        }


        // add back button
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    /**
     * Add a listener to the action bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        // check witch item is pressed.
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Response from asynctask to set tell users they must be patient.
     */
    public void updateProcess(String updateMessage){
        // check if one is already up
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        // make new one
        progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage(updateMessage);
        progressDialog.show();
    }

    /**
     * When asyncTask is finished, this function will be called. This function will recieve the data
     * from the api-call. It will be a String in jsonformat. It will take all information and put
     * it inside an new WeatherDay and add that WeatherDay to the arraylist.
     *
     * If an error happened result will contain the error message.
     */
    public void processFinish(String result){
        progressDialog.dismiss();

        try {
            // get sjon
            JSONObject jsonObject = new JSONObject(result);

            for (int i = 0; i < 7; i++) {
                JSONObject list = (JSONObject) jsonObject.getJSONArray("list").get(i);

                // date
                long unixTimestamp = list.getLong("dt");
                long javaTimestamp = unixTimestamp * 1000L;
                Date dateFormat = new Date(javaTimestamp);
                String date = new SimpleDateFormat("d MMM E").format(dateFormat);

                // temperatures
                JSONObject temp = list.getJSONObject("temp");
                String tempDay = temp.getString("day");
                String tempMin = temp.getString("min");
                String tempMax = temp.getString("max");
                String tempNight = temp.getString("night");
                String tempEve = temp.getString("eve");

                weatherDays.add(new WeatherDays(date, tempMax,tempMin, tempDay, tempNight, tempEve));


            }
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            Toast.makeText(SecondActivity.this, result, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


}
