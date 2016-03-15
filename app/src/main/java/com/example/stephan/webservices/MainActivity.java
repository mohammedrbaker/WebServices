package com.example.stephan.webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements TagAsyncTask.AsyncResponse {

    String dataDownloaded = "";
    TagAsyncTask asyncTask;
    TextView changeTest;
    TextView json;
    EditText userSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeTest = (TextView) findViewById(R.id.changeTextView);
        userSearch = (EditText) findViewById(R.id.userSearch);
        json = (TextView) findViewById(R.id.json);
    }

    public void onButtonClicked(View view){
        String search = userSearch.getText().toString();
        if(!search.isEmpty()){
            search = search.replace(" ", "");
            asyncTask = new TagAsyncTask();
            asyncTask.delegate = this;
            asyncTask.execute(search);
            Log.v("data", dataDownloaded);
        }
        else{
            Toast.makeText(MainActivity.this, "Please enter place", Toast.LENGTH_SHORT).show();
        }

    }

    public void processFinish(String output){
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(output);
            String place = jsonObject.getString("name");
            String country = jsonObject.getJSONObject("sys").getString("country");
            Double temparature = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            String text = "Place: " + place + "\n" + "Country: " + country + "\n" + "Temparature: " + temparature;
            changeTest.setText(text);
            json.setText(output);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

/*
TagAsyncTask: Get data.
    protected String onProgressesUpdate(){
        return  http...
    }

    protected void onPostExecute(){
    super.
    if no data report + error
    else but data in class


ClassForData: class voor de data.
OwnAdapter: show data.
HttpRequestHelper:
private static


Ideen:
Weer app
iets voor lol


 */